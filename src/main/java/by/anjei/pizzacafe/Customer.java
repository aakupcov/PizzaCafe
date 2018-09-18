package by.anjei.pizzacafe;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Customer {

    private List<Pizza> orders;
    private String name;

    public Customer(String name) {
        orders = new ArrayList<Pizza>();
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Pizza> getOrders() {
        return orders;
    }

    public boolean addOrder(Pizza p) {
        return orders.add(p);
    }

    public void removeOrder(Pizza p) {
        orders.remove(p);
    }

    public void changeName(String newName) {
        setName(newName);
    }

    void printOrders(BufferedWriter out) throws IOException {
        out.write("Here are all orders of mr/mrs " + this.getName() + ":");
        out.newLine();
        for (Pizza p : orders) {
            p.printPizza(out);
            out.newLine();
        }
    }

    void printTotalCost(BufferedWriter out) throws IOException {
       double totalCost = 0;
       for(Pizza p : orders){
           totalCost += p.getCost();
       }
        out.write("The total cost of mr/mrs " + this.getName() + 
                " is : $" + totalCost );
        out.newLine();
    }
}
