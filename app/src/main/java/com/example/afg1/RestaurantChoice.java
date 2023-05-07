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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_choice);

        //receive the max carbs
        Intent mIntent = getIntent();
        this.maxCarbs  =  mIntent.getExtras().getDouble("maxCarbs");
        Log.d("maxCarbs", "maxCarbs after passed = "+maxCarbs);
    }

    public void setDatabase(FirebaseDatabase database) {
        data = database;
    }

     //if user presses back button, going back to restaurant vs home meal page
    public void performRestaurantVsHome(View v) {
        Intent intent = new Intent(this, RestaurantVsHome.class);
        startActivity(intent);
    }

    //if user finishes choosing a restaurant, going to meal creation page
    public void performMealConstruction(View v) throws IOException {

        if (!validRestaurant){
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

    //if user presses home button, go to home
    public void performHomePage(View v) {
        Intent intent = new Intent(this, HomePage.class);
        startActivity(intent);
    }

    public void performWelcome(View v) {
        Intent intent = new Intent(this, Welcome.class);
        startActivity(intent);
    }

    public void performSearch(View v) {
        //fetches the text entered in the text view the first time
        EditText editText = (EditText) findViewById(R.id.searchBarRestaurant);
        String text = editText.getText().toString();
        //passes "text" to the search method below
        search(text);
        Log.d("RestaurantChoice", "Text #1 is: " + text);
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
                search(text);
                Log.d("RestaurantChoice", "Text #2 is: " + text);
                validRestaurant = false;
            }
        });
    }

    //should search through our database by sorting the children within a snapshot of our class according the the specified query and looping through the remaining children
    private void search(String name) {
        //correct the casing of the user input:
//        for (int i = 0; i<name.length(); i++){
//            if (i==0){
//                name = name.substring(0,0).toUpperCase()+name.substring(1);
//            }
//            else if (name.charAt(i)==' '){
//                name=name.substring(0,i+1)+name.substring(i+1, i+1).toUpperCase()+name.substring(i+2);
//            }
//        }
        restaurant = name;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Orders");

        Query query = myRef.orderByChild("restaurant").startAt(name).endAt(name);// + "\uf8ff");

        // For Firebase Realtime Database
        query.addListenerForSingleValueEvent(new ValueEventListener() { //filters the snapshot
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                int size = (int) snapshot.getChildrenCount();
                if (size > 0 && name!="" &&name !=" "&&name!=null) {
                    validRestaurant = true;
                }
                Log.d("RestaurantChoice", "Size post filtered: " + Integer.toString(size));
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