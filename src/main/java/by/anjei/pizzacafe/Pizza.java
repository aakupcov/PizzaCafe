package by.anjei.pizzacafe;

import java.io.BufferedWriter;
import java.io.IOException;

public class Pizza implements Comparable {

	private String type;
	private String topping;
	private int size;
	private double cost;

	public Pizza() {
		type = "unknown type";
		topping = "unknown topping";
		size = 0;
		cost = 0;
	}

	public Pizza(String type, String topping, int size, double cost) {
		this.type = type;
		this.topping = topping;
		this.size = size;
		this.cost = cost;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the topping
	 */
	public String getTopping() {
		return topping;
	}

	/**
	 * @param topping
	 *            the topping to set
	 */
	public void setTopping(String topping) {
		this.topping = topping;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * @return the cost
	 */
	public double getCost() {
		return cost;
	}

	/**
	 * @param cost
	 *            the cost to set
	 */
	public void setCost(double cost) {
		this.cost = cost;
	}

	public int compareTo(Object ob) {
		Pizza reg = (Pizza) ob;
		if (this.getSize() > reg.getSize()) {
			return 1;
		} else if (this.getSize() < reg.getSize()) {
			return -1;
		} else {
			return 0;
		}
	}

	public void printPizza(BufferedWriter out) throws IOException {
		out.write("The type of pizza is: " + this.getType());
		out.newLine();
		out.write("The topping of pizza is: " + this.getTopping());
		out.newLine();
		out.write("The size of pizza is: " + this.getPizzaSize());
		out.newLine();
		out.write("The cost of pizza is: $" + this.getCost());
	}

	private String getPizzaSize() {
		switch (this.getSize()) {
		case 1:
			return "small";
		case 2:
			return "mini";
		case 3:
			return "large";
		case 4:
			return "extraLarge";
		default:
			return "Unknown size";
		}
	}
}
