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
import java.util.List;
import javax.servlet.ServletException;
import web.Account;
import web.Product;
import web.Review;
import web.WebConnection;

/**
 *
 * @author Miko Garcia
 */
public class ReviewModel {
    private final ArrayList<Review> list;
    private final Connection con;
    private static ReviewModel instance = null;
    
    private ReviewModel()throws SQLException, ServletException {
        list = new ArrayList<>();
        con = WebConnection.getInstance().getDataSource().getConnection();
        updateModelList();
    }
    
    public static ReviewModel getInstance() throws SQLException, ServletException {
        if(instance == null) {
                instance = new ReviewModel();
        }

        return instance;
    }
    
    protected void updateModelList() throws SQLException, ServletException {
        list.removeAll(list);
        String sql = "SELECT * FROM " + Review.TABLE_NAME;
	ResultSet rs = con.prepareStatement(sql).executeQuery();
        
        rs.beforeFirst();
        while(rs.next()) {
            int reviewId = rs.getInt(Review.REVIEW_ID);
            int productId = rs.getInt(Review.PRODUCT_ID);
            int accountId = rs.getInt(Review.USER_ID);
            String review = rs.getString(Review.REVIEW);
            
           Account reviewer = AccountModel.getInstance().getAccountByID(accountId);
           
           Review toAdd = new Review.ReviewBuilder()
                            .reviewId(reviewId)
                            .productId(productId)
                            .review(review)
                            .account(reviewer)
                            .build();
           list.add(toAdd);
        }
    }
    
    public List<Review> getReviewsByProduct(int productId) {
        ArrayList<Review> reviews = new ArrayList<>();
        
        for(Review review: list) {
            if(review.getProductId() == productId) {
                reviews.add(review);
            }
        }
        
        return reviews;
    }
    
}