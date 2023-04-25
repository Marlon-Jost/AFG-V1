/**
 * Meal class holds information about each meal.
 * @author marlon
 *
 */
package com.example.afg1;

import java.util.ArrayList;

public class Meal {
	//Data
	private ArrayList<Order> meal;
	private double maxCarbs;
	private double servings; //may not be necessary
	//Constructors
	
	/**
	 * Default constructor. Sets each value to zero or empty 
	 */
	public Meal() {
		meal = new ArrayList<Order>();
		maxCarbs = 0;
		servings = 0;
	}

	/**
	 * parameterized constructor takes ArrayList of orders as input
	 * @param meal
	 */
	public Meal(ArrayList<Order> meal) {
		this.meal = meal;
	}
	//Methods

	/**
	 * @return the meal
	 */
	public ArrayList<Order> getMeal() {
		return meal;
	}

	/**
	 * @param meal the meal to set
	 */
	public void setMeal(ArrayList<Order> meal) {
		this.meal = meal;
	}

	/**
	 * @return the maxCarbs
	 */
	public double getMaxCarbs() {
		return maxCarbs;
	}

	/**
	 * @param maxCarbs the maxCarbs to set
	 */
	public void setMaxCarbs(double maxCarbs) {
		this.maxCarbs = maxCarbs;
	}

	/**
	 * @return the servings
	 */
	public double getServings() {
		return servings;
	}

	/**
	 * @param servings the servings to set
	 */
	public void setServings(double servings) {
		this.servings = servings;
	}
	
	public void addOrder(Order f) {
		meal.add(f);
	}
}
