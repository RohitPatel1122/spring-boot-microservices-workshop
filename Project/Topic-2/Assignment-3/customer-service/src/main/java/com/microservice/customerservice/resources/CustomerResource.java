package com.microservice.customerservice.resources;

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

import com.microservice.customerservice.model.CustomerModel;

@RestController()
@RequestMapping(value = "/customers")
public class CustomerResource {

	@Autowired
	RestTemplate restTemplate;
	@Autowired
	private Environment env;
	
	  @GetMapping() public CustomerModel[] getAllCustomers() {
	  System.out.println("------> Serving from customer-service running on port: "+env.getProperty("local.server.port"));
	  CustomerModel[] response =
	  restTemplate.getForObject("http://data-service/customers/",
	  CustomerModel[].class); return response; }
	  
	  @GetMapping(value = "/{id}") public CustomerModel
	  getCustomerById(@NotNull @PathVariable Long id) { 
		  System.out.println("------> Serving from customer-service running on port: "+env.getProperty("local.server.port"));
		  CustomerModel customerModel
	  = restTemplate.getForObject("http://data-service/customers/" + id,
	  CustomerModel.class); return customerModel; }
	 	
	@PostMapping()
	public CustomerModel saveCustomer(@RequestBody CustomerModel customer) {
		CustomerModel savedCustomer = restTemplate.postForObject("http://data-service/customers", customer,
				CustomerModel.class);
		return savedCustomer;
	}

	@PutMapping("/{id}")
	public ResponseEntity<CustomerModel> updateCustomer(@PathVariable Long id,
			@RequestBody CustomerModel updatedCustomer) {

		HttpEntity<CustomerModel> httpEntity = new HttpEntity<CustomerModel>(updatedCustomer);
		return restTemplate.exchange("http://data-service/customers/" + id, HttpMethod.PUT, httpEntity,
				CustomerModel.class);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity deleteCustomerById(@PathVariable Long id) {
		restTemplate.delete("http://data-service/customers/" + id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
