package com.example.afg1;

import static androidx.fragment.app.FragmentManager.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;


public class Restaurant {


    //Data
    private String name;
    private ArrayList<Order> orders;

    //Constructors
    public Restaurant() {
        this.name = "";
        this.orders = new ArrayList<Order>();
    }

    public Restaurant(String name) {
        this.name = name;
        this.orders = new ArrayList<Order>();


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("path/to/data");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Do something with each snapshot
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "onCancelled", databaseError.toException());
            }
        });


        for (Order o : //I want to step through the database here)

    }
//Methods
}
