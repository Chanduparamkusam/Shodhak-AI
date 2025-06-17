package com.ecomguard.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

@Service
public class ReviewAnalysisService {

    public String analyzeReview(String reviewText) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://127.0.0.1:5000/analyze"; // Python Flask API

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String json = "{\"review\":\"" + reviewText + "\"}";
        HttpEntity<String> request = new HttpEntity<>(json, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
        return response.getBody();
    }
}
