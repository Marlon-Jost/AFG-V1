package com.example.afg1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.InputStream;

public class Welcome extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    //after start screen, go to home
    public void performHomePage(View v) {
        EditText maxCarbs = findViewById(R.id.inputMaxCarbs);
        int numOfMaxCarbs = Integer.parseInt(maxCarbs.getText().toString());

        Button button = findViewById(R.id.button);

        Meal meal= new Meal();
        meal.setTotalCarbs(numOfMaxCarbs);
        Intent intent = new Intent(this, HomePage.class);
        startActivity(intent);
    }


}