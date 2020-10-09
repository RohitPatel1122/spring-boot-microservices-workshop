package com.microservice.customerservice.resources;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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

import com.microservice.customerservice.client.CustomerServiceFeignClient;
import com.microservice.customerservice.model.CustomerModel;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import lombok.extern.slf4j.Slf4j;

@RestController()
@RequestMapping(value = "/customers")
@Slf4j
public class CustomerResource {
	
	
	private static List<CustomerModel> listofCust = new ArrayList<>();

	static {
		for (long i = 1; i <= 5; i++) {
			CustomerModel newCustomer = new CustomerModel();
			newCustomer.setId(i);
			listofCust.add(newCustomer);
		}
	}

	@Autowired
	CustomerServiceFeignClient customerServiceFeignClient;
	@Autowired
	private Environment env;

	@GetMapping()
	@HystrixCommand(fallbackMethod = "defaultCustomers")
	public List<CustomerModel> getAllCustomers() {
		log.debug("------> Service running on port: " + env.getProperty("local.server.port"));
		return customerServiceFeignClient.getAllCustomers();
	}

	public List<CustomerModel> defaultCustomers() {

		return CustomerResource.listofCust;
	}

	@GetMapping(value = "/{id}")
	@HystrixCommand(fallbackMethod = "defaultCustomer")
	public CustomerModel getCustomerById(@PathVariable Long id) {
		System.out.println("------> Service running on port: " + env.getProperty("local.server.port"));

		return customerServiceFeignClient.getCustomerById(id);
	}

	public CustomerModel defaultCustomer(Long id) {
		CustomerResource.listofCust.get(0).setId(id);
		return CustomerResource.listofCust.get(0);
	}
	
	
	@PostMapping()
	// @HystrixCommand(fallbackMethod = "saveCustomerFallBack")
	public CustomerModel saveCustomer(@RequestBody CustomerModel customer) {
		System.out.println("------> Service running on port: " + env.getProperty("local.server.port"));
		return customerServiceFeignClient.saveCustomer(customer);
	}

	@PutMapping("/{id}")
	public ResponseEntity<CustomerModel> updateCustomer(@PathVariable Long id,

			@RequestBody CustomerModel updatedCustomer) {
		System.out.println("------> Service running on port: " + env.getProperty("local.server.port"));
		return customerServiceFeignClient.updateCustomer(id, updatedCustomer);

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteCustomerById(@PathVariable Long id) {
		System.out.println("------> Service running on port: " + env.getProperty("local.server.port"));
		customerServiceFeignClient.deleteCustomerById(id);
		return new ResponseEntity<String>("Deleted Successfully", HttpStatus.OK);
	}

}
