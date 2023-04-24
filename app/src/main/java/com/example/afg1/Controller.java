package com.example.afg1;

import android.app.Application;

import java.util.ArrayList;

public class Controller extends Application {

    static Meal meal = new Meal();
    ArrayList<Order> orders = new ArrayList<Order>(); //database may be a more appropriate name

    public ArrayList<Order> getOrders(){
        return orders;
    }
    public void addOrder(Order o) {
        orders.add(o);
    }
}
