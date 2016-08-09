/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.model;

import com.sun.istack.internal.logging.Logger;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.servlet.ServletException;
import web.LineItem;
import web.Order;
import web.Product;
import web.WebConnection;

/**
 *
 * @author user
 */
public class OrderModel {
    private final List<Order> list;
    private final Connection con;
    private static OrderModel instance = null;
    
    protected OrderModel() throws SQLException, ServletException {
        list = new ArrayList<>();
        con = WebConnection.getInstance().getDataSource().getConnection();
        updateModelList();
    }
    
    public static OrderModel getInstance() throws SQLException, ServletException {
            if(instance == null) {
                    instance = new OrderModel();
            }

            return instance;
    }

    private void updateModelList() throws SQLException {
        list.removeAll(list);
	String sql = "SELECT * FROM " + Order.TABLE_NAME + ";";
	ResultSet rs = con.prepareStatement(sql).executeQuery();
        
        rs.beforeFirst();
        while(rs.next()) {
            int orderId = rs.getInt(Order.ORDER_ID);
            int userId = rs.getInt(Order.USER_ID);
            Date orderDate = rs.getDate(Order.ORDER_DATE);
            double totalPrice = rs.getDouble(Order.TOTAL_PRICE);
            
            Order order = new Order.OrderBuilder()
                            .orderId(orderId)
                            .userId(userId)
                            .orderDate(orderDate)
                            .totalPrice(totalPrice)
                            .build();
            
            String itemSql = "SELECT * FROM " + LineItem.TABLE_NAME + 
                            " WHERE " + LineItem.ORDER_ID + " = " + orderId;
            ResultSet lineRs = con.prepareStatement(itemSql).executeQuery();
            
            lineRs.beforeFirst();
            while(lineRs.next()) {
                int lineId = lineRs.getInt(LineItem.LINE_ID);
                int productId = lineRs.getInt(LineItem.PRODUCT_ID);
                int qty = lineRs.getInt(LineItem.QTY);
                double lineTotal = lineRs.getDouble(LineItem.TOTAL_PRICE);
                
                LineItem item = new LineItem.LineItemBuilder()
                                    .lineId(lineId)
                                    .orderId(orderId)
                                    .productId(productId)
                                    .qty(qty)
                                    .totalPrice(lineTotal)
                                    .build();
                
                order.addLineItem(item);
            }
            
            list.add(order);
        }
    }
    
    public void addOrder(Order order) throws SQLException {
        String sql = "INSERT INTO " + Order.TABLE_NAME + "(" + 
                    Order.USER_ID + ", " + Order.TOTAL_PRICE + ") VALUES(?, ?);";
        
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, order.getUserId());
        ps.setDouble(2, order.getTotalPrice());
        
        ps.executeUpdate();
        
        String selectSql = "SELECT " + Order.ORDER_ID + " FROM " + Order.TABLE_NAME;
        ResultSet rs = con.prepareStatement(selectSql).executeQuery();
        rs.last();
        
        int orderId = rs.getInt(Order.ORDER_ID);
        String lineSql = "INSERT INTO " + LineItem.TABLE_NAME + "(" + 
                        LineItem.ORDER_ID + ", " + LineItem.PRODUCT_ID + ", " +
                        LineItem.QTY + ", " + LineItem.TOTAL_PRICE +
                        ") VALUES (?, ?, ?, ?);";
        PreparedStatement linePs = con.prepareStatement(lineSql);
        linePs.setInt(1, orderId);
        
        for(LineItem item: order.getLineItems()) {
            linePs.setInt(2, item.getProductId());
            linePs.setInt(3, item.getQty());
            linePs.setDouble(4, item.getTotalPrice());
            
            linePs.executeUpdate();
        }
    }
}