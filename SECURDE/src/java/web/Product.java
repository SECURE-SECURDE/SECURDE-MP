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
 * @author Miko Garcia
 */
public class Product {
    public static final String TABLE_NAME =             "products";
    public static final String PRODUCT_ID =             "product_id";
    public static final String PRODUCT_NAME =           "product_name";
    public static final String PRODUCT_DESCRIPTION =    "product_description";
    public static final String PRODUCT_PRICE =          "price";
    public static final String PRODUCT_DISCONTINUE =    "discont";
    public static final String PRODUCT_IMAGE =          "product_img";
    
    private int productId;
    private String productName;
    private String description;
    private double price;
    private boolean discontinue;
    private String imgDirectory;
    
    private List<Review> reviews;
    
    public static class ProductBuilder {
        private int productId = 0;
        private String productName;
        private String description = "";
        private double price = 0.00;
        private boolean discontinue = false;
        private String imgDirectory = "";
        private List<Review> reviews = new ArrayList<>();
        
        public ProductBuilder productId(int productId) {
            this.productId = productId;
            return this;
        } 
        
        public ProductBuilder productName(String productName) {
            this.productName = productName;
            return this;
        }
        
        public ProductBuilder description(String description) {
            this.description = description;
            return this;
        }
        
        public ProductBuilder discontinue(boolean discontinue) {
            this.discontinue = discontinue;
            return this;
        }
        
        public ProductBuilder imgDirectory(String imgDirectory) {
            this.imgDirectory = imgDirectory;
            return this;
        }
        
        public ProductBuilder price(double price) {
            this.price = price;
            return this;
        }
        
        public ProductBuilder reviews(List<Review> reviews) {
            this.reviews = reviews;
            return this;
        }
        
        public Product build() {
            return new Product(productId, productName, description, price, discontinue, imgDirectory, reviews);
        }
    }
    
    private Product(int productId, String productName, String description, double price, boolean discontinue, String imgDirectory, List<Review> reviews) {
        this.productId = productId;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.discontinue = discontinue;
        this.imgDirectory = imgDirectory;
        this.reviews = reviews;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() { 
        return price;
    }

    public boolean isDiscontinue() {
        return discontinue;
    }

    public String getImgDirectory() {
        return imgDirectory;
    }  
    
    public void addReview(Review review) {
        this.reviews.add(review);
    }
    
    public List<Review> getReviews() {
        return this.reviews;
    }
    
}
