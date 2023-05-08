/**
 * this class allows users to see their meal breakdown and add to it
 */

package com.example.afg1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
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
    private Double maxCarbs;

    /**
     * creates the meal breakdown page
     * accepts: meal, max carbs, and restaurant
     * @param savedInstanceState
     */
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
        String totCarbs = Double.toString(m.getTotalCarbs());
        displayCarbs.setText(totCarbs);

        //receive the max carbs
        maxCarbs = Double.parseDouble( mIntent.getExtras().getString("maxCarbs"));

        if(maxCarbs<m.getTotalCarbs()){
           displayCarbs.setTextColor(Color.RED);
        }
        else if(maxCarbs<m.getTotalCarbs()+(maxCarbs*0.5)){
            displayCarbs.setTextColor(Color.YELLOW);
        }
        else{
            displayCarbs.setTextColor(Color.GREEN);
        }

    }

    /**
     * returns to meal construction and passes on max carbs, restaurant, and the meal
     * @param v
     * @throws IOException
     */
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

        //pass on max carbs
        intent.putExtra("maxCarbs",  Double.toString(maxCarbs));

        startActivity(intent);
    }

    /**
     * returns to welcome page
     * @param v
     */
    public void performWelcome(View v) {
        Intent intent = new Intent(this, Welcome.class);
        startActivity(intent);
    }
}