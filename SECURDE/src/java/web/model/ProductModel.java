/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import web.LineItem;
import web.Order;
import web.Product;
import web.WebConnection;

/**
 *
 * @author Miko Garcia
 */
public class ProductModel {
    private final ArrayList<Product> list;
    private final Connection con;
    private static ProductModel instance = null;
    
    protected ProductModel() throws SQLException, ServletException {
        list = new ArrayList<>();
        con = WebConnection.getInstance().getDataSource().getConnection();
        
        updateModelList();
    }
    
    public static ProductModel getInstance() throws SQLException, ServletException {
            if(instance == null) {
                    instance = new ProductModel();
            }

            return instance;
    }
    
    private void updateModelList() throws SQLException, ServletException {
        list.removeAll(list);
	String sql = "SELECT * FROM " + Product.TABLE_NAME;
	ResultSet rs = con.prepareStatement(sql).executeQuery();
        
        rs.beforeFirst();
        while(rs.next()) {
            Product toAdd;
            
            int productId = rs.getInt(Product.PRODUCT_ID);
            String productName = rs.getString(Product.PRODUCT_NAME);
            String description = rs.getString(Product.PRODUCT_DESCRIPTION);
            String imgDirectory = rs.getString(Product.PRODUCT_IMAGE);
            double price = rs.getDouble(Product.PRODUCT_PRICE);
            boolean discontinue = rs.getInt(Product.PRODUCT_DISCONTINUE) == 1;
            
            toAdd = new Product.ProductBuilder()
                        .productId(productId)
                        .price(price)
                        .productName(productName)
                        .description(description)
                        .imgDirectory(imgDirectory)
                        .discontinue(discontinue)
                        .reviews(ReviewModel.getInstance().getReviewsByProduct(productId))
                        .build();
            
            list.add(toAdd);
        }
        
        rs.close();
    }
    
    public Product getProductById(int productId) {
        for(Product prod: list) {
            if(prod.getProductId() == productId) {
                return prod;
            }
        }
        
        return null;
    }
    
    public void addProduct(Product product) throws SQLException, ServletException {
        String sql = "INSERT INTO " + Product.TABLE_NAME + " (" +
                    Product.PRODUCT_NAME + ", " + Product.PRODUCT_PRICE + ", " +
                    Product.PRODUCT_DESCRIPTION + ") VALUES (?, ?, ?);";
        
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, product.getProductName());
        ps.setDouble(2, product.getPrice());
        ps.setString(3, product.getDescription());
        
        ps.executeUpdate();
        ps.close();
        
        updateModelList();
    }
        
    public boolean boughtByUser(int productId, int userId) throws SQLException {
        String sql = "SELECT * FROM " + LineItem.TABLE_NAME + " L, " + 
                    Order.TABLE_NAME + " O WHERE O." + Order.ORDER_ID + " = L." + 
                    LineItem.ORDER_ID + " AND O." + Order.USER_ID + " = ? AND L." +
                    LineItem.PRODUCT_ID + " = ?;";
        
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, userId);
        ps.setInt(2, productId);
        
        ResultSet rs = ps.executeQuery();
        
        boolean bought = rs.isBeforeFirst();
        
        ps.close();
        rs.close();
        
        return bought;
    }
}
