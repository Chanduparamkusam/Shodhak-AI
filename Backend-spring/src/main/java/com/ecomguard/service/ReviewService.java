package com.ecomguard.service;

import com.ecomguard.model.Review;

import java.util.List;

public interface ReviewService {
    Review save(Review review);
    List<Review> getAll();
    Review getById(Long id);
    List<Review> getByProductId(Long productId);
    List<Review> getByUserId(Long userId);
    Review update(Long id, Review review);
    List<Review> deleteById(Long id);
}
