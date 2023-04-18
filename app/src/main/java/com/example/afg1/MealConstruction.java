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
}