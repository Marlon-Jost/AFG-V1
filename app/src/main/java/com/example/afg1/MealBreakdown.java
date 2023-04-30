package com.example.afg1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class MealBreakdown extends AppCompatActivity {

    private Meal m;
    private String restaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_breakdown);

        //Get restaurant string
        Intent mIntent = getIntent();
        restaurant = mIntent.getExtras().getString("restaurantString");

        //get meal
        byte[] bytes =  mIntent.getExtras().getByteArray("meal");

        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(bis);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            m = (Meal)in.readObject();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        TextView displayOrders = findViewById(R.id.idDisplayOrdersText);
        String text = m.getOrderNames();
        displayOrders.setText(text);

        TextView displayCarbs = findViewById(R.id.textNumCarbs);
        text = Double.toString(m.getTotalCarbs());
        displayCarbs.setText(text);

    }

    public void performHomePage(View v) {
        Intent intent = new Intent(this, HomePage.class);
        startActivity(intent);
    }

    public void performMealConstruction(View v) throws IOException {
        Intent intent = new Intent(this, MealConstruction.class);

        //pass on restaurant string
        intent.putExtra("restaurantString", restaurant);

        //pass on meal
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bos);
        out.writeObject(m);
        byte[] bytes = bos.toByteArray();
        //m.setRestaurant(restaurant);
        intent.putExtra("meal", bytes);

        startActivity(intent);
    }
}