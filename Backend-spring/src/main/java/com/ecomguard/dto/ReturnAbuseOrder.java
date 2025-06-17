package com.ecomguard.dto;

import java.time.LocalDate;

public class ReturnAbuseOrder {

    private Long id;
    private String orderId;
    private String userId;
    private String productId;   // Add productId
    private String returnreason;
    private boolean suspicious;  // Add suspicious flag
    private LocalDate returnDate; // Add returnDate
    private int returnCount;
    private int totalOrders;

    // Getters and Setters

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProductId() {
        return productId;
    }
    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getreturnReason() {
        return returnreason;
    }
    public void setReason(String reason) {
        this.returnreason = reason;
    }

    public boolean isSuspicious() {
        return suspicious;
    }
    public void setSuspicious(boolean suspicious) {
        this.suspicious = suspicious;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }
    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public int getReturnCount() {
        return returnCount;
    }
    public void setReturnCount(int returnCount) {
        this.returnCount = returnCount;
    }

    public int getTotalOrders() {
        return totalOrders;
    }
    public void setTotalOrders(int totalOrders) {
        this.totalOrders = totalOrders;
    }
}
