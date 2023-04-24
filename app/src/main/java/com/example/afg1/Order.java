/**
 * Class Order contains the information about each order on a menu
 * 
 * @author marlon
 *
 */
package com.example.afg1;

public class Order {
	// Data
	private double totalCarbs;
	private double servingSize;
	//private double servingNum;
	private String servingUnits;
	private String orderName;
	private String restaurant;

	// Constructors
	/**
	 * Default constructor sets each value to 0 or empty
	 */
	public Order() {
		totalCarbs = 0;
		servingSize = 0;
		//servingNum = 0;
		servingUnits = "";
		restaurant = "";
		orderName = "";
	}

	/**
	 * Creates an order object based on the input. the input should be in the form
	 * of "Restaurant, Order name, Total Carbs (in grams), Serving size, Serving
	 * units"
	 * 
	 * @param restaurant
	 * @param orderName
	 * @param totalCarbs
	 * @param servingSize
	 * @param servingUnits
	 */
	public Order(String restaurant, String orderName, double totalCarbs, double servingSize, String servingUnits) {
		this.restaurant = restaurant;
		this.orderName = orderName;
		this.totalCarbs = totalCarbs;
		this.servingSize = servingSize;
		this.servingUnits = servingUnits;
	}

	// Methods

	/**
	 * @return the orderName
	 */
	public String getOrderName() {
		return orderName;
	}

	/**
	 * @param orderName the orderName to set
	 */
	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	/**
	 * @return the totalCarbs
	 */
	public double getTotalCarbs() {
		return totalCarbs;
	}

	/**
	 * @param totalCarbs the totalCarbs to set
	 */
	public void setTotalCarbs(double totalCarbs) {
		this.totalCarbs = totalCarbs;
	}

	/**
	 * @return the servingSize
	 */
	public double getServingSize() {
		return servingSize;
	}

	/**
	 * @param servingSize the servingSize to set
	 */
	public void setServingSize(double servingSize) {
		this.servingSize = servingSize;
	}

//	/**
//	 * @return the servingNum
//	 */
//	public double getServingNum() {
//		return servingNum;
//	}
//
//	/**
//	 * @param servingNum the servingNum to set
//	 */
//	public void setServingNum(double servingNum) {
//		this.servingNum = servingNum;
//	}

	/**
	 * @return the servingUnits
	 */
	public String getServingUnits() {
		return servingUnits;
	}

	/**
	 * @param servingUnits the servingUnits to set
	 */
	public void setServingUnits(String servingUnits) {
		this.servingUnits = servingUnits;
	}
	/**
	 * returns the name of the restaurant as a string
	 * @return name of the restaurant
	 */
	public String getRestaurant(){
		return restaurant;
	}
}
