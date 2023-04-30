package com.example.afg1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_construction);

        Intent mIntent = getIntent();
        restaurant = mIntent.getExtras().getString("restaurantString");

        byte[] bytes =  mIntent.getExtras().getByteArray("meal");

        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(bis);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            m = (Meal)in.readObject();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //receive the max carbs
        maxCarbs = Double.parseDouble( mIntent.getExtras().getString("maxCarbs"));
    }

    //if user presses home button, go back to home page (need another button to save meal if it doesn't save automatically)
    public void performHomePage(View v) {
        Intent intent = new Intent(this, HomePage.class);
        startActivity(intent);
    }

    //if user presses back button, go back to restaurant vs home page
    public void performRestaurantVsHomePage(View v) {
        Intent intent = new Intent(this, RestaurantVsHome.class);
        startActivity(intent);
    }

//    //if user wants to add a food, go to food search page
//    public void performFoodSearch(View v) {
//        Intent intent = new Intent(this, FoodSearch.class);
//        startActivity(intent);
//    }

    //if user finishes adding custom order, save info (wip) and go to meal breakdown page
    public void performMealBreakdown(View v) throws IOException {
        Intent intent = new Intent(this, MealBreakdown.class);
        intent.putExtra("restaurantString", restaurant);

        //get the strings from the user input
        EditText inputFoodName = findViewById(R.id.inputFoodName);
        String orderName = inputFoodName.getText().toString();

        EditText inputServingSize = findViewById(R.id.inputServingSize);
        double servingSize = Double.parseDouble( inputServingSize.getText().toString());

        EditText idInputServingUnits = findViewById(R.id.idInputServingUnits);
        String servingUnits = idInputServingUnits.getText().toString();

        EditText inputGramsOfCarbs = findViewById(R.id.inputGramsOfCarbs);
        double carbs = Double.parseDouble( inputGramsOfCarbs.getText().toString());

        EditText inputServingPercentage = findViewById(R.id.inputServingPercentage);
        double servingPercentage = Double.parseDouble( inputServingPercentage.getText().toString());

        //calculate carbs and serving size based on serving percentage
        carbs=(servingPercentage/100.0)*carbs;
        servingSize=(servingPercentage/100.0)*servingSize;

        //make order
        order = new Order(restaurant, orderName, carbs, servingSize, servingUnits );

        //pass on meal object
        m.addOrder(order);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bos);
        out.writeObject(m);
        byte[] bytes = bos.toByteArray();
        intent.putExtra("meal", bytes);

        //pass on max carbs
        intent.putExtra("maxCarbs",  Double.toString(maxCarbs));

        startActivity(intent);
    }


    public void performSearch(View v) {
        //fetches the text entered in the text view the first time
        EditText editText = (EditText) findViewById(R.id.inputFoodName);
        String text = editText.getText().toString();
        //passes "text" to the search method below
        search(text);
        Log.d("MealConstruction", "Text #1 is: " + text);

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
            }
        });
    }

    //should search through our database by sorting the children within a snapshot of our class according the the specified query and looping through the remaining children
    private void search(String foodName) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Orders");

        //myRef.orderByChild("restaurant").startAt(restaurant).endAt(restaurant + "\uf8ff");
        Query query = myRef.orderByChild("orderName").startAt(foodName).endAt(foodName + "\uf8ff");

        query.addListenerForSingleValueEvent(new ValueEventListener() { //filters the snapshot
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                int size = (int) snapshot.getChildrenCount();
                Log.d("MealConstruction", "Size post filtered: " + Integer.toString(size));


                if (snapshot.exists()) {
                    for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                        Log.d("MealConstruction", //"Key is: " + childSnapshot.getKey() +
                                " value is: " + childSnapshot.getValue());

                        if (size == 1) {
                            String text = "";
                            Order o = childSnapshot.getValue(Order.class);
//                            order = o;
                            if (o.getRestaurant().equals(restaurant)) {
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
                            }
                        }
                        else{
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