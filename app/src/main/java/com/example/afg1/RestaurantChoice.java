package com.example.afg1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class RestaurantChoice extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_choice);

    }

    private static final String[] RESTAURANTS = new String[] {
            "panera", "starbucks", "taco bell"
    };


    //if user presses back button, going back to restaurant vs home meal page
    public void performRestaurantVsHome(View v) {
        Intent intent = new Intent(this, RestaurantVsHome.class);
        startActivity(intent);
    }

    //if user finishes choosing a restaurant, going to meal creation page
    public void performMealConstruction(View v) {
        Intent intent = new Intent(this, MealConstruction.class);
        startActivity(intent);
    }

    //if user presses home button, go to home
    public void performHomePage(View v) {
        Intent intent = new Intent(this, HomePage.class);
        startActivity(intent);
    }
}