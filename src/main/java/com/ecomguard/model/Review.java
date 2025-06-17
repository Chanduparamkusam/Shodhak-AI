package com.ecomguard.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long userId;
    private long productId;

    @Column(length = 3000)
    private String reviewText;

    private int rating;
    private boolean detectedAsFake;
    private double score;

    @Column(nullable = false)
    private boolean suspicious;
    @Column(name = "timestamp", nullable = false)

    private LocalDateTime timestamp;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public long getUserId() { return userId; }
    public void setUserId(long userId) { this.userId = userId; }

    public long getProductId() { return productId; }
    public void setProductId(long productId) { this.productId = productId; }

    public String getReviewText() { return reviewText; }
    public void setReviewText(String reviewText) { this.reviewText = reviewText; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public boolean isDetectedAsFake() { return detectedAsFake; }
    public void setDetectedAsFake(boolean detectedAsFake) { this.detectedAsFake = detectedAsFake; }

    public double getScore() { return score; }
    public void setScore(double score) { this.score = score; }

    public boolean isSuspicious() { return suspicious; }
    public void setSuspicious(boolean suspicious) { this.suspicious = suspicious; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
