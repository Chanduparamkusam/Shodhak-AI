package com.ecomguard.controller;

import com.ecomguard.dto.ReturnAbuseOrder;
import com.ecomguard.dto.ReturnAbuseRequest;
import com.ecomguard.dto.ReturnAbuseResponse;
import com.ecomguard.service.ReturnAbuseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/return-abuse")
public class ReturnAbusecontroller {   // Fixed class name typo

    @Autowired
    private ReturnAbuseService returnAbuseService;

    // 1. Save a return abuse order
    @PostMapping
    public ResponseEntity<ReturnAbuseOrder> createReturnAbuse(@RequestBody ReturnAbuseOrder order) {
        ReturnAbuseOrder savedOrder = returnAbuseService.createRequest(order);
        return ResponseEntity.ok(savedOrder);
    }

    // 2. Get all return abuse orders
    @GetMapping
    public ResponseEntity<List<ReturnAbuseOrder>> getAllRequests() {
        List<ReturnAbuseOrder> orders = returnAbuseService.getAllRequests();
        return ResponseEntity.ok(orders);
    }

    // 3. AI detection of return abuse for multiple requests
    @PostMapping("/detect")
    public ResponseEntity<ReturnAbuseResponse> detectReturnAbuse(@RequestBody List<ReturnAbuseRequest> requests) {
        List<ReturnAbuseResponse> responses = returnAbuseService.detectReturnAbuse(requests);

        if (responses.isEmpty()) {
            ReturnAbuseResponse defaultResponse = new ReturnAbuseResponse();
            defaultResponse.setSuspicious(false);
            defaultResponse.setMessage("No data provided.");
            return ResponseEntity.ok(defaultResponse);
        }

        return ResponseEntity.ok(responses.get(0)); // Only returning the first one
    }


    // 4. Get single order by ID
    @GetMapping("/{id}")
    public ResponseEntity<ReturnAbuseOrder> getRequestById(@PathVariable Long id) {
        ReturnAbuseOrder order = returnAbuseService.getRequestById(id);
        if (order == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(order);
    }

    // 5. Delete by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRequest(@PathVariable Long id) {
        returnAbuseService.deleteRequest(id);
        return ResponseEntity.noContent().build();
    }

    // 6. Update existing order
    @PutMapping("/{id}")
    public ResponseEntity<ReturnAbuseOrder> updateRequest(
            @PathVariable Long id,
            @RequestBody ReturnAbuseOrder updatedOrder
    ) {
        ReturnAbuseOrder updated = returnAbuseService.updateRequest(id, updatedOrder);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    // 7. Test endpoint
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        System.out.println("Test endpoint hit!");
        return ResponseEntity.ok("Test successful");
    }
}
