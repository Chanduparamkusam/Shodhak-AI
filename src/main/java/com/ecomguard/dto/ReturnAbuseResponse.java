package com.ecomguard.dto;

public class ReturnAbuseResponse {
    private String userId;
    private String orderId;
    private String productId;
    private boolean suspicious;
    private String reason;
    private String message;
    private double suspiciousScore;  // ✅ New field

    // Getters and setters
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }

    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }

    public boolean isSuspicious() { return suspicious; }
    public void setSuspicious(boolean suspicious) { this.suspicious = suspicious; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public double getSuspiciousScore() { return suspiciousScore; }
    public void setSuspiciousScore(double suspiciousScore) { this.suspiciousScore = suspiciousScore; }
}
