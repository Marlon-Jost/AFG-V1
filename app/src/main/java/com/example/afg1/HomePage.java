package com.example.afg1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class HomePage extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World!");


        //The line below adds to Firebase when ran (Can be commented out when database isn't being updated)
        //readQuestionDataField();



    }

    @Override
    protected void onStart(){
        super.onStart();

        readQuestionDataFB();
        //Get the Global Controller class object
        final Controller aController = (Controller) getApplicationContext();

        // Write a message to the database (Can be commented out when database isn't being updated)
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Orders");

        for(Order o: aController.getOrders()){
            Log.v("HomePage", "Order: "+o.getOrderName());

            //Code to push data to Firebase (Can be commented out when database isn't being updated)
            myRef.push().setValue(o);
        }
    }
    private void readQuestionDataFB(){

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Orders");

        //Get the Global Controller class object
        final Controller aController = (Controller) getApplicationContext();

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    Order o = ds.getValue(Order.class);
                    aController.addOrder(o);
                    Log.d("HomePage", "Order is: " + o.getOrderName());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w("HomePage", "Failed to read value.", error.toException());
            }
        });
    }

    private void readQuestionDataField(){
        InputStream is = getResources().openRawResource(R.raw.self_made_excel_database_1);

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String line = "";

        //Get the Global Controller class object
        final Controller aController = (Controller) getApplicationContext();

        try{
            while ((line = reader.readLine()) != null){
                //split by comma
                String[] feilds = line.split(",");
                //Log.v("HomePage", feilds[0] + " " +feilds[1] + " " +feilds[2] + " " +feilds[3] + " " +feilds[4]);
                Order o = new Order(feilds[0], feilds[1], Integer.parseInt(feilds[2]), Integer.parseInt(feilds[3]), feilds[4] );
                aController.addOrder(o);
            }
        }
        catch(IOException e){
            Log.wtf("HomePage", "Error reading data on line:"+ line);

        }
    }

    //If user wants to add a meal, first gets sent to restaurant vs home page
    public void performRestaurantVsHome(View v) {
        Intent intent = new Intent(this, RestaurantVsHome.class);
        startActivity(intent);
    }
}