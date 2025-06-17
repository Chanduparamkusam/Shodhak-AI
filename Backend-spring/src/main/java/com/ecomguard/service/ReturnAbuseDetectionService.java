package com.ecomguard.service;

import com.ecomguard.dto.*;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ReturnAbuseDetectionService {

    private final RestTemplate restTemplate = new RestTemplate();
   private static final String FLASK_URL = "https://shodhak-ai.onrender.com/detect-return-abuse";

    public ReturnAbuseResponse detectAbuse(ReturnAbuseRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        HttpEntity<ReturnAbuseRequest> entity = new HttpEntity<>(request, headers);
        
        ResponseEntity<ReturnAbuseResponse> response = restTemplate.exchange(
            FLASK_URL,
            HttpMethod.POST,
            entity,
            ReturnAbuseResponse.class
        );
        
        return response.getBody();
    }
}
