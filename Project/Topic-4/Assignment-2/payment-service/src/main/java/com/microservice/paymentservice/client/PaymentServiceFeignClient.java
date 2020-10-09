package com.microservice.paymentservice.client;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.microservice.paymentservice.model.PaymentModel;

@FeignClient(name="${dataservice.service-id}")
@RequestMapping("/paymentsdata")
public interface PaymentServiceFeignClient {

	@GetMapping("")
	List<PaymentModel> getAllPayments();
	
	@GetMapping("/{id}")
	PaymentModel getPaymentById(@NotNull @PathVariable(value= "id") Long id);
	
	@GetMapping("/customer/{customerid}")
	public List<PaymentModel> getAllPaymentsForCustomer(@NotNull @PathVariable(value= "customerid") Long customerid) ;
	
	@PostMapping("")
	public PaymentModel savePayment(@RequestBody PaymentModel payment);
	
	@PutMapping("/{id}")
	public PaymentModel updatePayment(@PathVariable(value= "id") Long id, @RequestBody PaymentModel updatedPayment);
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletePaymentById(@PathVariable(value= "id") Long id);
	
}
