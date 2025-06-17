package com.ecomguard.dto;

import java.util.List;

public class ReturnAbuseRequest {
    private String userId;
    private int returnWindowDays = 7;
    private List<Order> orders;

    // Getters and setters
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public int getReturnWindowDays() {
        return returnWindowDays;
    }
    public void setReturnWindowDays(int returnWindowDays) {
        this.returnWindowDays = returnWindowDays;
    }
    public List<Order> getOrders() {
        return orders;
    }
    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public static class Order {
        private String orderId;
        private String orderDate;  // Format: "yyyy-MM-dd"
        private List<Product> products;

        // Getters and setters
        public String getOrderId() {
            return orderId;
        }
        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }
        public String getOrderDate() {
            return orderDate;
        }
        public void setOrderDate(String orderDate) {
            this.orderDate = orderDate;
        }
        public List<Product> getProducts() {
            return products;
        }
        public void setProducts(List<Product> products) {
            this.products = products;
        }
    }

    public static class Product {
        private String productId;
        private String category;
        private String returnDate;  // Format: "yyyy-MM-dd"
        private String returnType;
        private String reason;
        // Getters and setters
        public String getProductId() {
            return productId;
        }
        public void setProductId(String productId) {
            this.productId = productId;
        }
        public String getCategory() {
            return category;
        }
        public void setCategory(String category) {
            this.category = category;
        }
        public String getReturnDate() {
            return returnDate;
        }
        public void setReturnDate(String returnDate) {
            this.returnDate = returnDate;
        }
        public String getReturnType() {
            return returnType;
        }
        public void setReturnType(String returnType) {
            this.returnType = returnType;
        }
		public String getReason() {
			return reason;
		}
		public void setReason(String reason) {
			this.reason = reason;
		}
		
		
		}
    }
