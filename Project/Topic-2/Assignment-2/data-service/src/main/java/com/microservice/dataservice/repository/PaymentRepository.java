package com.microservice.dataservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservice.dataservice.entity.PaymentEntity;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
	
	List<PaymentEntity> findByCustomerId(Long customerId);
	Optional<PaymentEntity> findByPaymentIdAndCustomerId(Long id, Long CustomerId);
	
}
