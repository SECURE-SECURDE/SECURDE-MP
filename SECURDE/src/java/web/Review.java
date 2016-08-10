/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

/**
 *
 * @author Miko Garcia
 */
public class Review {
    public final static String TABLE_NAME =     "reviews";
    public final static String REVIEW_ID =      "review_id";
    public final static String USER_ID =        "user_id";
    public final static String PRODUCT_ID =     "product_id";
    public final static String REVIEW =         "review";
    
    private int reviewId;
    private String review;
    private int userId;
    private int productId;
    
    public static class ReviewBuilder {
        private int reviewId = 0;
        private String review;
        private int userId;
        private int productId;
        
        public ReviewBuilder reviewId(int reviewId) {
            this.reviewId = reviewId;
            return this;
        }
        
        public ReviewBuilder review(String review) {
            this.review = review;
            return this;
        }
        
        public ReviewBuilder userId(int userId) {
            this.userId = userId;
            return this;
        }
        
        public ReviewBuilder productId(int productId) {
            this.productId = productId;
            return this;
        }
        
        public Review build() {
            return new Review(reviewId, review, userId, productId);
        }
    }
    
    private Review(int reviewId, String review, int userId, int productId) {
        this.reviewId = reviewId;
        this.review = review;
        this.userId = userId;
        this.productId = productId;
    }

    public int getReviewId() {
        return reviewId;
    }

    public String getReview() {
        return review;
    }

    public int getUserId() {
        return userId;
    }

    public int getProductId() {
        return productId;
    }
    
}
