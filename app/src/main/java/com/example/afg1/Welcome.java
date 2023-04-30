package com.example.afg1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Welcome extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    //after start screen, go to home
    public void performHomePage(View v) {
        Intent intent = new Intent(this, HomePage.class);
        startActivity(intent);
    }

    //if user is choosing from menu in restaurant
    public void performRestaurantChoice(View v) {
        Intent intent = new Intent(this, RestaurantChoice.class);

        EditText editText = findViewById(R.id.inputMaxCarbs);
        String maxCarbs = editText.getText().toString();

        //pass on max carbs
        intent.putExtra("maxCarbs", maxCarbs);

        startActivity(intent);
    }

}