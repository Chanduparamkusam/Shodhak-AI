package com.ecomguard.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReviewRequest {
    private long userId;
    private long productId;
    @JsonProperty("review")
    private String reviewtext;
    private int rating;

    // Getters and Setters
    public long getUserId() { return userId; }

    public void setUserId(long userId) { this.userId = userId; }

    public long getProductId() { return productId; }

    public void setProductId(long productId) { this.productId = productId; }

    public String getReviewtext() { return reviewtext; }

    public void setReviewtext(String review) { this.reviewtext = review; }

    public int getRating() { return rating; }

    public void setRating(int rating) { this.rating = rating; }

}