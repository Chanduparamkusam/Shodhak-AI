package com.ecomguard.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReviewResponse {

    @JsonProperty("userId")
    private String userId;

    @JsonProperty("productId")
    private String productId;

    @JsonProperty("fake")
    private boolean fake;

    @JsonProperty("score")
    private double score;

    @JsonProperty("message")
    private String message;

    // Getters and Setters
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }

    public boolean isFake() { return fake; }
    public void setFake(boolean fake) { this.fake = fake; }

    public double getScore() { return score; }
    public void setScore(double score) { this.score = score; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
