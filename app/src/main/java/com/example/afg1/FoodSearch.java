package com.example.afg1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class FoodSearch extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_search);
    }

    //once food choice is finished or is cancelled, go back to meal construction page
    public void performMealConstruction(View v) {
        Intent intent = new Intent(this, MealConstruction.class);
        startActivity(intent);
    }

    //if user presses home button, go to welcome screen
    public void performHomePage(View v) {
        Intent intent = new Intent(this, Welcome.class);
        startActivity(intent);
    }
}