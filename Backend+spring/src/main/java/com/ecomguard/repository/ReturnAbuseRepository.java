package com.ecomguard.repository;

import com.ecomguard.model.ReturnAbuse;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReturnAbuseRepository extends JpaRepository<ReturnAbuse, Long> {
    // must be entity class ReturnAbuse here
	Optional<ReturnAbuse> findByOrderId(String orderId);

}
