/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
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
    
    protected void updateModelList() throws SQLException, ServletException {
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
    }
    
    public Product getProductById(int productId) {
        for(Product prod: list) {
            if(prod.getProductId() == productId) {
                return prod;
            }
        }
        
        return null;
    }
}
