package by.anjei.pizzacafe;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class PizzaCafe {

	public static Scanner in = new Scanner(System.in);
	public static Customer human = null;
	public static BufferedWriter out = null;
	public static List<Customer> clients = new ArrayList<Customer>();

	public static void main(String[] args) {

		try {
			out = new BufferedWriter(new FileWriter("src\\pizzaoutput.txt"));
			getInfo();
			printInfo(out);
		} catch (IOException e) {
			try {
				e.printStackTrace(new PrintWriter("src\\pizzaerrors.txt"));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		}finally{
			if(out != null ){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static void printInfo(BufferedWriter out) throws IOException {
		for (Customer c : clients) {
			c.printOrders(out);
			c.printTotalCost(out);
			out.newLine();
		}

	}

	private static void getInfo() {
		String customerName = null;
		do {
			System.out.println("Hello, dear guest, we like to see you.\nWhat is your name?");
			customerName = in.nextLine();
			human = new Customer(customerName);
			makeOrder(human);
			clients.add(human);
		} while (isClient());
	}

	private static void makeOrder(Customer human) {
		String pizzaType;
		int pizzaSize;
		String topping;
		double cost;

		System.out.println("Please, make your order");
		boolean enough = false;

		while (!enough) {
			pizzaType = enterPizzaType();
			topping = chooseTopping(pizzaType);
			pizzaSize = choosePizzaSize(pizzaType);
			cost = calculateCost(pizzaType, pizzaSize);

			Pizza order = new Pizza(pizzaType, topping, pizzaSize, cost);
			human.addOrder(order);
			enough = isEnough();
		}
	}

	private static double calculateCost(String pizzaType, int pizzaSize) {
		Map<String, Double> menu = getMenu(pizzaType);
		String size;
		switch (pizzaSize) {
		case 1:
			size = "small";
			break;
		case 2:
			size = "mini";
			break;
		case 3:
			size = "large";
			break;
		case 4:
			size = "extraLarge";
			break;
		default:
			size = "Unknown size";
			break;
		}
		if (menu.containsKey(size)) {
			return menu.get(size);
		} else {
			return -1;
		}
	}

	private static int choosePizzaSize(String pizzaType) {
		Map<String, Double> menu = getMenu(pizzaType);
		String pizzaSize = correctPizzaSizeChoice(menu);

		switch (pizzaSize.toLowerCase()) {
		case "small":
			return 1;
		case "mini":
			return 2;
		case "large":
			return 3;
		case "extralarge":
			return 4;
		default:
			return 0;
		}

	}

	private static boolean isEnough() {

		String answer = "";
		while (true) {
			System.out.println("Would you like more pizza: yes/no");
			answer = in.nextLine();
			if (answer.equalsIgnoreCase("Yes")) {
				return false;
			} else if (answer.equalsIgnoreCase("No")) {
				return true;
			} else {
				System.out.println("The wrong answer.");
			}
		}
	}

	private static Map<String, Double> getMenu(String pizzaType) {
		Scanner inFile = null;
		StringBuilder pizzaSize = new StringBuilder("");
		Map<String, Double> menu = new LinkedHashMap<String, Double>();
		try {
			if (pizzaType.equalsIgnoreCase("Regular")) {
				inFile = new Scanner(new File("src\\resources\\regular_price.txt"));
			} else {
				inFile = new Scanner(new File("src\\resources\\sicilian_price.txt"));
			}

			while (inFile.hasNextLine()) {
				pizzaSize.replace(0, pizzaSize.length(), inFile.nextLine());
				int index = pizzaSize.indexOf(" ");
				menu.put(pizzaSize.substring(0, index),
						Double.valueOf(pizzaSize.substring(index + 1, pizzaSize.length())));
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (inFile != null) {
				inFile.close();
			}
		}

		return menu;

	}

	private static String enterPizzaType() {

		String pizzaType = "";
		while (true) {
			System.out.println("Enter the type of pizza: Regular/Sicilian");
			pizzaType = in.nextLine();
			if (pizzaType.equalsIgnoreCase("Regular")) {
				return "Regular";
			} else if (pizzaType.equalsIgnoreCase("Sicilian")) {
				return "Sicilian";
			} else {
				System.out.println("The incorrect pizza type");
			}
		}

	}

	private static String chooseTopping(String pizzaType) {
		Scanner inFile = null;
		try {
			if (pizzaType.equalsIgnoreCase("Regular")) {
				inFile = new Scanner(new File("src\\resources\\regular_toppings.txt"));
			} else {
				inFile = new Scanner(new File("src\\resources\\sicilian_toppings.txt"));
			}
			List<String> toppings = new ArrayList<String>();
			while (inFile.hasNextLine()) {
				toppings.add(inFile.nextLine());
			}

			return correctToppingsChoice(toppings);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (inFile != null) {
				inFile.close();
			}
		}
		return null;
	}

	private static String correctToppingsChoice(List<String> toppings) {
		while (true) {
			System.out.println("Choose one of those toppings:");
			for (String a : toppings) {
				System.out.println(a);
			}

			String topping = in.nextLine();
			if (toppings.contains(topping)) {
				return topping;
			} else {
				System.out.println("Incorrect choice.");
			}
		}
	}

	private static String correctPizzaSizeChoice(Map<String, Double> menu) {
		String pizzaSize = "";

		while (true) {
			System.out.println("Choose the size of pizza you would like:");
			for (String s : menu.keySet()) {
				System.out.println(s + " " + menu.get(s));
			}
			pizzaSize = in.nextLine();
			if (menu.containsKey(pizzaSize)) {
				return pizzaSize;
			} else {
				System.out.println("You entered the incorrect size. Do it again.");
			}

		}

	}

	private static boolean isClient() {
		while (true) {
			System.out.println("Is there any client: Yes/No");
			String answer = in.nextLine();
			if (answer.equalsIgnoreCase("Yes")) {
				return true;
			} else if (answer.equalsIgnoreCase("No")) {
				return false;
			} else {
				System.out.println("The incorrect answer. Try again.");
			}
		}
	}
}
