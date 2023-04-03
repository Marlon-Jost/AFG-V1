package com.example.afg1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class RestaurantVsHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_vs_home);
    }

    //if user is making homemade meals (skip restaurant choice screen)
    public void performMealConstruction(View v) {
        Intent intent = new Intent(this, MealConstruction.class);
        startActivity(intent);
    }

    //if user is choosing from menu in restaurant (buttons for this don't exist yet)
    public void performRestaurantChoice(View v) {
        Intent intent = new Intent(this, RestaurantChoice.class);
        startActivity(intent);
    }

    //if user presses back / home button, cancelling creation of meal
    public void performHomePage(View v) {
        Intent intent = new Intent(this, HomePage.class);
        startActivity(intent);
    }
}