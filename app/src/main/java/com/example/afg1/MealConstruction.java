/**
 * this class allows users to search for an order and/or to input their own data
 */
package com.example.afg1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class MealConstruction extends AppCompatActivity {

    private String restaurant;
    private Meal m;
    private Order order;
    private double maxCarbs;

    private boolean filledName;

    private boolean validOrder;

    /**
     * creates meal construction page, accepts restaurant, meal, and max carbs
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_construction);

        Intent mIntent = getIntent();
        restaurant = mIntent.getExtras().getString("restaurantString");

        byte[] bytes = mIntent.getExtras().getByteArray("meal");

        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(bis);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            m = (Meal) in.readObject();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //receive the max carbs
        maxCarbs = Double.parseDouble(mIntent.getExtras().getString("maxCarbs"));
        Log.d("maxCarbs", "maxCarbs = " + maxCarbs);

        filledName = false;
        validOrder = false;
    }

    /**
     * creates restaurant choice page and passes on max carbs
     * @param v
     */
    public void performRestaurantChoice(View v) {
        Intent intent = new Intent(this, RestaurantChoice.class);

        Log.d("maxCarbs", "maxCarbs before passed = " + maxCarbs);

        //pass on max carbs
        intent.putExtra("maxCarbs", maxCarbs);

        startActivity(intent);
    }

    /**
     * creates welcome page
     * @param v
     */
    public void performWelcome(View v) {
        Intent intent = new Intent(this, Welcome.class);

        intent.putExtra("want to return to restaurant choice", true);

        startActivity(intent);
    }


    /**
     * reads the text fields and passes on the data to meal breakdown class
     * @param v
     * @throws IOException
     */
    public void performMealBreakdown(View v) throws IOException {

        if (!validOrder) {
            Toast.makeText(getApplicationContext(), "Pick valid order please (first letter of each word should be capitalized)", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, MealBreakdown.class);
        intent.putExtra("restaurantString", restaurant);

        //get the strings from the user input
        EditText inputFoodName = findViewById(R.id.inputFoodName);
        String orderName;

        //catch empty input
        if (!inputFoodName.getText().toString().isEmpty()) {
            orderName = inputFoodName.getText().toString();
        } else {
            orderName = "blank";
        }

        EditText inputServingSize = findViewById(R.id.inputServingSize);
        double servingSize;

        //catch empty input
        if (!inputServingSize.getText().toString().isEmpty()) {
            servingSize = Double.parseDouble(inputServingSize.getText().toString());
        } else {
            servingSize = 0;
        }

        EditText idInputServingUnits = findViewById(R.id.idInputServingUnits);
        String servingUnits;

        //catch empty input
        if (!idInputServingUnits.getText().toString().isEmpty()) {
            servingUnits = idInputServingUnits.getText().toString();
        } else {
            servingUnits = "0";
        }

        EditText inputGramsOfCarbs = findViewById(R.id.inputGramsOfCarbs);
        double carbs;

        //catch empty input
        if (!inputGramsOfCarbs.getText().toString().isEmpty()) {
            carbs = Double.parseDouble(inputGramsOfCarbs.getText().toString());
        } else {
            carbs = 0;
        }

        EditText inputServingPercentage = findViewById(R.id.inputServingPercentage);
        double servingPercentage;

        //catch empty input
        if (!inputServingPercentage.getText().toString().isEmpty()) {
            servingPercentage = Double.parseDouble(inputServingPercentage.getText().toString());
        } else {
            servingPercentage = 0;
        }

        //calculate carbs and serving size based on serving percentage
        carbs = (double)((double)servingPercentage / 100.0) * (double)carbs;
        servingSize = (double)((double)servingPercentage / 100.0) * (double) servingSize;

        //make order
        order = new Order(restaurant, orderName, carbs, servingSize, servingUnits);

        //pass on meal object
        m.addOrder(order);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bos);
        out.writeObject(m);
        byte[] bytes = bos.toByteArray();
        intent.putExtra("meal", bytes);

        //pass on max carbs
        intent.putExtra("maxCarbs", Double.toString(maxCarbs));

        startActivity(intent);
    }

    /**
     * Observes user input into the text box and calls the search method on changes
     *
     * @param v
     */
    public void performSearch(View v) {
        //fetches the text entered in the text view the first time
        EditText editText = (EditText) findViewById(R.id.inputFoodName);
        String text = editText.getText().toString();
        //passes "text" to the search method below
        search(text);
        Log.d("MealConstruction", "Text #1 is: " + text);
        filledName = false;
        //continually updates the value of "text" as the user edits the input text
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Do nothing
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Call your method here with the text entered by the user
                String text = s.toString();
                //passes "text" to the search method below
                search(text);
                Log.d("MealConstruction", "Text #2 is: " + text);
                validOrder = false;
            }
        });
    }

    /**
     * searches through our database by sorting the children within a snapshot of our class
     * according the the specified query and looping through the remaining children
     */
     private void search(String foodName) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Orders");

        //myRef.orderByChild("restaurant").startAt(restaurant).endAt(restaurant + "\uf8ff");
        Query query = myRef.orderByChild("orderName").startAt(foodName).endAt(foodName + "\uf8ff");

        query.addListenerForSingleValueEvent(new ValueEventListener() { //filters the snapshot
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                int size = (int) snapshot.getChildrenCount();
                Log.d("MealConstruction", "Size post filtered: " + (size));


                if (snapshot.exists()) {
                    for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                        Log.d("MealConstruction", //"Key is: " + childSnapshot.getKey() +
                                "value is: " + childSnapshot.getValue());

                        if (size == 1) {
                            validOrder = true;
                            String text = "";
                            Order o = childSnapshot.getValue(Order.class);
//                            order = o;
                            if (o.getRestaurant().equals(restaurant)) {
                                Log.d("OrderExists", "order o = " + o.toString());
                                //Display serving size (number)
                                EditText editText = findViewById(R.id.inputServingSize);
                                text = Double.toString(o.getServingSize());
                                editText.setText(text);

                                //Display serving size (number)
                                EditText editText4 = findViewById(R.id.idInputServingUnits);
                                text = o.getServingUnits();
                                editText4.setText(text);

                                //Display carbs per serving
                                EditText editText2 = findViewById(R.id.inputGramsOfCarbs);
                                text = Double.toString(o.getTotalCarbs());// + " carbs";
                                editText2.setText(text);

                                //Display 1 for serving size by default
                                EditText editText3 = findViewById(R.id.inputServingPercentage);
                                editText3.setText("100");
                                if (!filledName) {
                                    //Autofill food name
                                    EditText editText5 = findViewById(R.id.inputFoodName);
                                    editText5.setText(o.getOrderName());
                                    filledName = true;
                                }
                            }
                        } else {
                            String text = "";
                            Order o = childSnapshot.getValue(Order.class);
                            //Display serving size (number) as blank
                            EditText editText = findViewById(R.id.inputServingSize);
                            text = "";
                            editText.setText(text);

                            //Display serving size (number) as blank
                            EditText editText4 = findViewById(R.id.idInputServingUnits);
                            text = "";
                            editText4.setText(text);

                            //Display carbs per serving as blank
                            EditText editText2 = findViewById(R.id.inputGramsOfCarbs);
                            text = "";
                            editText2.setText(text);

                            //Display 1 for serving size by default as blank
                            EditText editText3 = findViewById(R.id.inputServingPercentage);
                            editText3.setText("");
                        }
                    }
                } else {
                    Log.d("MealConstruction", "No data available");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.err.println(error.getMessage());
            }
        });

    }

}