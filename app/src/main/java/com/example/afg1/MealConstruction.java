package com.example.afg1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MealConstruction extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_construction);

        final Controller aController = (Controller) getApplicationContext();
        EditText inputFoodName = findViewById(R.id.inputFoodName);

        inputFoodName.setText("Apples"); //find how to transfer data from order class
        //names rn are placeholders
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
}