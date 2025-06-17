package com.ecomguard.service;

import com.ecomguard.dto.ReturnAbuseOrder;
import com.ecomguard.dto.ReturnAbuseRequest;
import com.ecomguard.dto.ReturnAbuseResponse;

import java.util.List;

public interface ReturnAbuseService {
    ReturnAbuseOrder createRequest(ReturnAbuseOrder request);
    List<ReturnAbuseOrder> getAllRequests();
    ReturnAbuseOrder getRequestById(Long id);
    ReturnAbuseOrder updateRequest(Long id, ReturnAbuseOrder request);
    void deleteRequest(Long id);
    List<ReturnAbuseResponse> detectReturnAbuse(List<ReturnAbuseRequest> request);


}
