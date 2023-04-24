package com.example.afg1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MealBreakdown extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_breakdown);

        TextView textNumCarbs = findViewById(R.id.textNumCarbs);

        textNumCarbs.setText(Double.toString(Controller.meal.getTotalCarbs()));
        double totalCarbs = Controller.meal.getTotalCarbs();
    }

    public void performHomePage(View v) {
        Intent intent = new Intent(this, HomePage.class);
        startActivity(intent);
    }

    public void performMealConstruction(View v) {
        Intent intent = new Intent(this, MealConstruction.class);
        startActivity(intent);
    }
}