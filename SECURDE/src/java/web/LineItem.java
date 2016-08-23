/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

/**
 *
 * @author user
 */
public class LineItem {
    public final static String TABLE_NAME = "line_items";
    
    public final static String LINE_ID = "line_id";
    public final static String ORDER_ID = "order_id";
    public final static String PRODUCT_ID = "product_id";
    public final static String QTY = "qty";
    public final static String TOTAL_PRICE = "total_price";
    
    private int lineId;
    private int orderId;
    private Product product;
    private int qty;
    
    public static class LineItemBuilder {
        private int lineId = 0;
        private int orderId = 0;
        private Product product;
        private int qty = 0;
        
        public LineItemBuilder lineId(int lineId) {
            this.lineId = lineId;
            return this;
        }
        
        public LineItemBuilder orderId(int orderId) {
            this.orderId = orderId;
            return this;
        }
        
        public LineItemBuilder product(Product product) {
            this.product = product;
            return this;
        }
        
        public LineItemBuilder qty(int qty) {
            this.qty = qty;
            return this;
        }
        
        public LineItem build() {
            return new LineItem(lineId, orderId, product, qty);
        }
    }

    public LineItem(int lineId, int orderId, Product product, int qty) {
        this.lineId = lineId;
        this.orderId = orderId;
        this.product = product;
        this.qty = qty;
    }

    public int getLineId() {
        return lineId;
    }

    public int getOrderId() {
        return orderId;
    }

    public Product getProduct() {
        return product;
    }

    public int getQty() {
        return qty;
    }

    public double getTotalPrice() {
        return qty * product.getPrice();
    }
    
    
    
}
