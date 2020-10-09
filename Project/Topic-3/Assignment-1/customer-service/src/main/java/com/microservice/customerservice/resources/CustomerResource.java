package com.microservice.customerservice.resources;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.microservice.customerservice.client.CustomerServiceFeignClient;
import com.microservice.customerservice.model.CustomerModel;

@RestController()
@RequestMapping(value = "/customers")
public class CustomerResource {

	
	@Autowired
	CustomerServiceFeignClient customerServiceFeignClient;
	@Autowired
	private Environment env;

	@GetMapping()
	public List<CustomerModel> getAllCustomers() {
		System.out.println(
				"------> Service running on port: " + env.getProperty("local.server.port"));
		return customerServiceFeignClient.getAllCustomers();
	}

	@GetMapping(value = "/{id}")
	public CustomerModel getCustomerById(@NotNull @PathVariable Long id) {
		System.out.println(
				"------> Service running on port: " + env.getProperty("local.server.port"));

		return customerServiceFeignClient.getCustomerById(id);
	}

	@PostMapping()
	public CustomerModel saveCustomer(@RequestBody CustomerModel customer) {
		System.out.println(
				"------> Service running on port: " + env.getProperty("local.server.port"));
		return customerServiceFeignClient.saveCustomer(customer);
	}

	@PutMapping("/{id}")
	public ResponseEntity<CustomerModel> updateCustomer(@PathVariable Long id,

			@RequestBody CustomerModel updatedCustomer) {
		System.out.println(
				"------> Service running on port: " + env.getProperty("local.server.port"));
		return customerServiceFeignClient.updateCustomer(id, updatedCustomer);
		
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteCustomerById(@PathVariable Long id) {
		System.out.println(
				"------> Service running on port: " + env.getProperty("local.server.port"));
		customerServiceFeignClient.deleteCustomerById(id);
		return new ResponseEntity<String>("Deleted Successfully",HttpStatus.OK);
	}

}
