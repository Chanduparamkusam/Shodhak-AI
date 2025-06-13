package com.ecomguard.controller;

import com.ecomguard.dto.ReviewRequest;
import com.ecomguard.dto.ReviewResponse;
import com.ecomguard.model.Review;
import com.ecomguard.repository.ReviewRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewRepository repository;

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String FLASK_REVIEW_URL = "http://localhost:5000/detect-fake-review";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/analyze")
    public ResponseEntity<ReviewResponse> analyzeReview(@RequestBody ReviewRequest request) {
        try {
            System.out.println("Sending to Flask: " + objectMapper.writeValueAsString(request));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<ReviewRequest> entity = new HttpEntity<>(request, headers);
            ResponseEntity<ReviewResponse> flaskResponse = restTemplate.exchange(
                    FLASK_REVIEW_URL,
                    HttpMethod.POST,
                    entity,
                    ReviewResponse.class
            );

            ReviewResponse result = flaskResponse.getBody();
            System.out.println("Flask response received: " + objectMapper.writeValueAsString(result));

            // Save to DB
            Review review = new Review();
            review.setUserId(request.getUserId());
            review.setProductId(request.getProductId());
            review.setReviewText(request.getReviewtext());
            review.setRating(request.getRating());
            review.setDetectedAsFake(result.isFake());
            review.setScore(result.getScore());
            review.setTimestamp(LocalDateTime.now());
            repository.save(review);

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            ReviewResponse error = new ReviewResponse();
            error.setMessage("Error analyzing review: " + e.getMessage());
            error.setFake(false);
            error.setScore(0.0);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}
