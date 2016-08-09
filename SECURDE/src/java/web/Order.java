/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author user
 */
public class Order {
    public final static String TABLE_NAME = "orders";
    
    public final static String ORDER_ID = "order_id";
    public final static String USER_ID = "user_id";
    public final static String ORDER_DATE = "order_date";
    public final static String TOTAL_PRICE = "total_price";
    
    private int orderId;
    private int userId;
    private Date orderDate;
    private double totalPrice;
    private List<LineItem> orderItems;
    
    public static class OrderBuilder {
        private int orderId = 0;
        private int userId;
        private Date orderDate;
        private double totalPrice;
        private List<LineItem> orderItems = new ArrayList<>();
        
        public OrderBuilder orderId(int orderId) {
            this.orderId = orderId;
            return this;
        }
        
        public OrderBuilder userId(int userId) {
            this.userId = userId;
            return this;
        }
        
        public OrderBuilder orderDate(Date orderDate) {
            this.orderDate = orderDate;
            return this;
        }
        
        public OrderBuilder totalPrice(double totalPrice) {
            this.totalPrice = totalPrice;
            return this;
        }
        
        public OrderBuilder orderItems(List orderItems) {
            this.orderItems = orderItems;
            return this;
        }
        
        public Order build() {
            return new Order(orderId, userId, orderDate, totalPrice, orderItems);
        }
    }

    private Order(int orderId, int userId, Date orderDate, double totalPrice, List orderItems) {
        this.orderId = orderId;
        this.userId = userId;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.orderItems = orderItems;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getUserId() {
        return userId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
    
    public void addLineItem(LineItem item) {
        this.orderItems.add(item);
    }
    
    public List<LineItem> getLineItems() {
        return this.orderItems;
    }
}
