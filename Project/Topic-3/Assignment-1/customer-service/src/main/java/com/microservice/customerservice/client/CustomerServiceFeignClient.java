package com.microservice.customerservice.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.microservice.customerservice.model.CustomerModel;

@FeignClient(name = "${dataservice.service-id}")
@RequestMapping("/customersdata")
public interface CustomerServiceFeignClient {
	@GetMapping()
	List<CustomerModel> getAllCustomers();

	@GetMapping("/{id}")
	CustomerModel getCustomerById(@PathVariable(value = "id") Long id);

	@PostMapping()
	CustomerModel saveCustomer(@RequestBody CustomerModel customer);

	@PutMapping("/{id}")
	public ResponseEntity<CustomerModel> updateCustomer(@PathVariable(value = "id") Long id,
			@RequestBody CustomerModel updatedCustomer);

	@DeleteMapping("/{id}")
	public void deleteCustomerById(@PathVariable(value = "id") Long id);

}