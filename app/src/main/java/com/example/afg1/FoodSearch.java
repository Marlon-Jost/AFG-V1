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

    //if user presses home button, go to home
    public void performHomePage(View v) {
        Intent intent = new Intent(this, HomePage.class);
        startActivity(intent);
    }


}