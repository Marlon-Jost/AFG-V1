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

public class MealConstruction extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_construction);
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

    //if user wants to add a food, go to food search page
    public void performFoodSearch(View v) {
        Intent intent = new Intent(this, FoodSearch.class);
        startActivity(intent);
    }

    //if user finishes adding custom order, save info (wip) and go to meal breakdown page
    public void performMealBreakdown(View v) {
        Intent intent = new Intent(this, MealBreakdown.class);
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
    private void search(String name) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Orders");

        Query query = myRef.orderByChild("orderName").startAt(name).endAt(name + "\uf8ff");

        // For Firebase Realtime Database
        query.addListenerForSingleValueEvent(new ValueEventListener() { //filters the snapshot
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                int size = (int) snapshot.getChildrenCount();
                Log.d("MealConstruction","Size post filtered: " + Integer.toString(size));
                if (snapshot.exists()) {
                    for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                        Log.d("MealConstruction", "Key is: " + childSnapshot.getKey() + " value is: " + childSnapshot.getValue());
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