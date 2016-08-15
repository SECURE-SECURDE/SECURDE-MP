/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author user
 */
public class Cart {
    public final static String ATTRIBUTE_NAME = "SHOPPING_CART";
    
    private List<LineItem> items;
    private double totalPrice;
    
    public Cart() {
        items = new ArrayList<>();
    }
    
    public void addItem(LineItem item) {
        items.add(item);
        totalPrice += item.getTotalPrice();
    }
    
    public List<LineItem> getItems() {
        return this.items;
    }
    
    public double getTotalPrice() {
        return this.totalPrice;
    }
}
