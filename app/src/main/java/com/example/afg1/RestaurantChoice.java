/**
 * allows users to search for a restaurant
 */

package com.example.afg1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class RestaurantChoice extends AppCompatActivity {

    private FirebaseDatabase data;

    private String restaurant;

    private double maxCarbs;

    private boolean validRestaurant;

    /**
     * creates restaurant choice page and accepts max carbs from welcome page
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_choice);

        //receive the max carbs
        Intent mIntent = getIntent();
        this.maxCarbs = mIntent.getExtras().getDouble("maxCarbs");
        Log.d("maxCarbs", "maxCarbs after passed = " + maxCarbs);
    }

    public void setDatabase(FirebaseDatabase database) {
        data = database;
    }

    /**
     * Creates a meal construction page, passes on: meal, restaurant, and max carbs
     *
     * @param v
     * @throws IOException
     */
    public void performMealConstruction(View v) throws IOException {

        if (!validRestaurant) {
            Toast.makeText(getApplicationContext(), "Pick valid restaurant please", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, MealConstruction.class);

        //pass on restaurant string
        intent.putExtra("restaurantString", restaurant);

        //pass on meal object
        Meal m = new Meal();
        m.setRestaurant(restaurant);
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
     * creates welcome page
     *
     * @param v
     */
    public void performWelcome(View v) {
        Intent intent = new Intent(this, Welcome.class);
        startActivity(intent);
    }

    /**
     * Observes user input into the text box and calls the search method on changes
     *
     * @param v
     */
    public void performSearch(View v) {
        //fetches the text entered in the text view the first time
        EditText editText = (EditText) findViewById(R.id.searchBarRestaurant);
        String text = editText.getText().toString();

        //standardize text string capitalisation
        for (int i =0; i<text.length(); i++){
            if ( i==0){
                text = Character.toUpperCase(text.charAt(i))+text.substring(i+1);
            } else if (text.charAt(i-1) == ' ') {
                text= text.substring(0, i)+Character.toUpperCase(text.charAt(i))+text.substring(i+1);
            }
            if (text.equals("kfc")){
                text= text.substring(0, i)+Character.toUpperCase(text.charAt(i))+text.substring(i+1);
            }
        }

        //kfc to caps
        if (text.equals("kfc")){
            text = text.toUpperCase();
        }

        //passes "text" to the search method below
        search(text);
        Log.d("caps testing", "Text #1 is: " + text);
        validRestaurant = false;

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

                //standardize text string capitalisation
                for (int i =0; i<text.length(); i++){
                    if ( i==0){
                        text = Character.toUpperCase(text.charAt(i))+text.substring(i+1);
                    } else if (text.charAt(i-1) == ' ') {
                    text= text.substring(0, i)+Character.toUpperCase(text.charAt(i))+text.substring(i+1);
                    }
                    if (text.equals("kfc")){
                        text= text.substring(0, i)+Character.toUpperCase(text.charAt(i))+text.substring(i+1);
                    }
                }


                search(text);
                Log.d("caps testing", "Text #2 is: " + text);
                validRestaurant = false;
            }
        });
    }

    /**
     * searches through our database by sorting the children within a snapshot of our class
     * according the the specified query and looping through the remaining children
     */
    private void search(String name) {

        restaurant = name;

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Orders");

        Query query = myRef.orderByChild("restaurant").startAt(name).endAt(name);// + "\uf8ff");

        // For Firebase Realtime Database
        query.addListenerForSingleValueEvent(new ValueEventListener() { //filters the snapshot
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                int size = (int) snapshot.getChildrenCount();
                if (size > 0 && name != "" && name != " " && name != null) {
                    validRestaurant = true;
                }
                Log.d("RestaurantChoice", "Size post filtered: " + (size));
                if (snapshot.exists()) {
                    for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                        Log.d("RestaurantChoice", "Key is: " + childSnapshot.getKey() + " value is: " + childSnapshot.getValue());
                    }
                } else {
                    Log.d("RestaurantChoice", "No data available");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.err.println(error.getMessage());
            }
        });

    }
}