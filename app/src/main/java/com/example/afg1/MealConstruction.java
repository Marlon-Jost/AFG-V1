package com.example.afg1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MealConstruction extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_construction);
    }

    //if user presses back button / home button, go back to home page (need another button to save meal if it doesn't save automatically)
    public void performHomePage(View v) {
        Intent intent = new Intent(this, MealConstruction.class);
        startActivity(intent);
    }

    //if user wants to add a food, go to food search page
    public void performFoodSearch(View v) {
        Intent intent = new Intent(this, FoodSearch.class);
        startActivity(intent);
    }
}