package com.ecomguard.service;

import com.ecomguard.dto.ReturnAbuseOrder;
import com.ecomguard.dto.ReturnAbuseRequest;
import com.ecomguard.dto.ReturnAbuseResponse;
import com.ecomguard.model.ReturnAbuse;
import com.ecomguard.repository.ReturnAbuseRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReturnAbuseServiceImpl implements ReturnAbuseService {

    @Autowired
    private ReturnAbuseRepository repository;

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String FLASK_URL = "http://localhost:5000/detect-return-abuse";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public ReturnAbuseOrder createRequest(ReturnAbuseOrder orderDto) {
        ReturnAbuse entity = new ReturnAbuse();
        entity.setUserId(orderDto.getUserId());
        entity.setOrderId(orderDto.getOrderId());
        entity.setProductId(orderDto.getProductId());
        entity.setReason(orderDto.getreturnReason());
        entity.setReturnDate(LocalDate.now());
        entity.setSuspicious(false);

        ReturnAbuse saved = repository.save(entity);
        return mapToDto(saved);
    }

    @Override
    public List<ReturnAbuseOrder> getAllRequests() {
        return repository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ReturnAbuseOrder getRequestById(Long id) {
        return repository.findById(id)
                .map(this::mapToDto)
                .orElse(null);
    }

    @Override
    public void deleteRequest(Long id) {
        repository.deleteById(id);
    }

    @Override
    public ReturnAbuseOrder updateRequest(Long id, ReturnAbuseOrder updatedOrder) {
        return repository.findById(id)
                .map(entity -> {
                    entity.setUserId(updatedOrder.getUserId());
                    entity.setOrderId(updatedOrder.getOrderId());
                    entity.setProductId(updatedOrder.getProductId());
                    entity.setReason(updatedOrder.getreturnReason());
                    return mapToDto(repository.save(entity));
                })
                .orElse(null);
    }

    @Override
    public List<ReturnAbuseResponse> detectReturnAbuse(List<ReturnAbuseRequest> requests) {
        System.out.println("[INFO] Flattening data and sending to Flask...");

        List<Map<String, Object>> flatList = new ArrayList<>();

        for (ReturnAbuseRequest request : requests) {
            if (request.getOrders() != null) {
                for (var order : request.getOrders()) {
                    if (order.getProducts() != null) {
                        for (var product : order.getProducts()) {
                            Map<String, Object> item = new HashMap<>();
                            item.put("userId", request.getUserId());
                            item.put("orderId", order.getOrderId());
                            item.put("productId", product.getProductId());
                            item.put("reason", product.getReason());
                            item.put("returnDate", product.getReturnDate());
                            item.put("returnType", product.getReturnType());
                            item.put("category", product.getCategory());
                            item.put("orderDate", order.getOrderDate());
                            flatList.add(item);
                        }
                    }
                }
            }
        }

        try {
            // Debug log
            String debugPayload = objectMapper.writeValueAsString(flatList);
            System.out.println("[DEBUG] Payload to Flask:\n" + debugPayload);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<List<Map<String, Object>>> entity = new HttpEntity<>(flatList, headers);

            ResponseEntity<ReturnAbuseResponse[]> response = restTemplate.exchange(
                    FLASK_URL,
                    HttpMethod.POST,
                    entity,
                    ReturnAbuseResponse[].class
            );

            ReturnAbuseResponse[] resultArray = response.getBody();
            if (resultArray == null) {
                System.out.println("[ERROR] Flask response body is null");
                return List.of(createErrorResponse("Flask returned null response"));
            }

            List<ReturnAbuseResponse> results = Arrays.asList(resultArray);

            // Fix missing IDs and add message and suspiciousScore
            for (int i = 0; i < results.size(); i++) {
                ReturnAbuseResponse result = results.get(i);
                Map<String, Object> original = flatList.get(i);

                if (result.getUserId() == null || result.getUserId().isEmpty())
                    result.setUserId(Objects.toString(original.get("userId"), ""));

                if (result.getOrderId() == null || result.getOrderId().isEmpty())
                    result.setOrderId(Objects.toString(original.get("orderId"), ""));

                if (result.getProductId() == null || result.getProductId().isEmpty())
                    result.setProductId(Objects.toString(original.get("productId"), ""));

                // Set message
                result.setMessage("Detection complete");

                // Set suspiciousScore if missing
                if (result.getSuspiciousScore() == 0 && original.containsKey("suspiciousScore")) {
                    try {
                        double score = Double.parseDouble(original.get("suspiciousScore").toString());
                        result.setSuspiciousScore(score);
                    } catch (Exception ignore) {
                        // ignore parse error
                    }
                }
            }

            // Update suspicious entries in DB
            for (ReturnAbuseResponse result : results) {
                if (result.isSuspicious()) {
                    Optional<ReturnAbuse> optional = repository.findByOrderId(result.getOrderId());
                    if (optional.isPresent()) {
                        ReturnAbuse abuse = optional.get();
                        abuse.setSuspicious(true);
                        repository.save(abuse);
                        System.out.println("[DB UPDATED] Marked suspicious: Order ID = " + result.getOrderId());
                    } else {
                        System.out.println("[WARN] No matching DB record for Order ID = " + result.getOrderId());
                    }
                }
            }

            return results;

        } catch (Exception e) {
            System.out.println("[EXCEPTION] Flask error: " + e.getMessage());
            return List.of(createErrorResponse("Error communicating with Python service: " + e.getMessage()));
        }
    }

    private ReturnAbuseResponse createErrorResponse(String message) {
        ReturnAbuseResponse error = new ReturnAbuseResponse();
        error.setMessage(message);
        error.setSuspicious(false);
        return error;
    }

    private ReturnAbuseOrder mapToDto(ReturnAbuse entity) {
        ReturnAbuseOrder dto = new ReturnAbuseOrder();
        dto.setId(entity.getId());
        dto.setUserId(entity.getUserId());
        dto.setOrderId(entity.getOrderId());
        dto.setProductId(entity.getProductId());
        dto.setReason(entity.getReason());
        dto.setSuspicious(entity.isSuspicious());
        dto.setReturnDate(entity.getReturnDate());
        return dto;
    }
}
