package com.example.afg1;

import android.app.Application;

import java.util.ArrayList;

public class Controller extends Application {

    ArrayList<Order> meal = new ArrayList<Order>(); //database may be a more appropriate name

    public ArrayList<Order> getOrders(){
        return meal;
    }
    public void addOrder(Order o){
        meal.add(o);
    }
}
