package com.ecomguard.service;

import com.ecomguard.dto.ReviewRequest;
import com.ecomguard.dto.ReviewResponse;
import com.ecomguard.model.Review;
import com.ecomguard.repository.ReviewRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String FLASK_URL = "https://shodhak-ai.onrender.com/detect-fake-review";



    private final ObjectMapper objectMapper = new ObjectMapper();

    public ReviewResponse analyzeReview(ReviewRequest request) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<ReviewRequest> entity = new HttpEntity<>(request, headers);

            ResponseEntity<ReviewResponse> response = restTemplate.exchange(
                    FLASK_URL,
                    HttpMethod.POST,
                    entity,
                    ReviewResponse.class
            );

            ReviewResponse result = response.getBody();

            // Save review to DB
            Review review = new Review();
            review.setUserId(request.getUserId());
            review.setProductId(request.getProductId());
            review.setReviewText(request.getReviewtext());
            review.setRating(request.getRating());
            review.setDetectedAsFake(result.isFake()); // ✅ uses returned value
            review.setScore(result.getScore());
            review.setSuspicious(result.isFake());     // ✅ optional if you store it separately
            review.setTimestamp(LocalDateTime.now());

            reviewRepository.save(review);

            return result;
        } catch (Exception e) {
            e.printStackTrace();
            ReviewResponse error = new ReviewResponse();
            error.setMessage("Error analyzing review: " + e.getMessage());
            error.setFake(false);
            error.setScore(0.0);
            return error;
        }
    }

    @Override
    public Review save(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getAll() {
        return reviewRepository.findAll();
    }

    @Override
    public Review getById(Long id) {
        return reviewRepository.findById(id).orElse(null);
    }

    @Override
    public List<Review> getByProductId(Long productId) {
        return reviewRepository.findByProductId(productId);
    }

    @Override
    public List<Review> getByUserId(Long userId) {
        return reviewRepository.findByUserId(userId);
    }

    @Override
    public Review update(Long id, Review review) {
        Review existing = getById(id);
        if (existing == null) return null;

        existing.setReviewText(review.getReviewText());
        existing.setRating(review.getRating());
        existing.setDetectedAsFake(review.isDetectedAsFake());
        existing.setScore(review.getScore());
        existing.setSuspicious(review.isSuspicious());
        return reviewRepository.save(existing);
    }

    @Override
    public List<Review> deleteById(Long id) {
        reviewRepository.deleteById(id);
        return getAll();
    }
}
