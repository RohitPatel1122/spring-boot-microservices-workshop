package com.microservice.dataservice.resources;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.dataservice.entity.CustomerEntity;
import com.microservice.dataservice.exceptions.NoSuchCustomerFoundException;
import com.microservice.dataservice.repository.CustomerRepository;

@RestController
@RequestMapping(value = "/customers")
public class CustomerResources {

	@Autowired
	CustomerRepository customerRepository;
	@GetMapping()
	public List<CustomerEntity> getAllCustomers(){
		
		return customerRepository.findAll();
	}
	
	@GetMapping(value = "/{id}")
	public Optional<CustomerEntity> getCustomerById(@PathVariable Long id){
		
		return getCustomer(id);
	}
	
	
	private Optional<CustomerEntity> getCustomer(Long id){
		
		return customerRepository.findById(id);
	}
	
	@PostMapping()
	public CustomerEntity saveCustomer(@RequestBody CustomerEntity customerEntity) {
		return customerRepository.save(customerEntity);
	}
	
	@PutMapping(value = "/{id}")
	public CustomerEntity updateCustomer(@PathVariable Long id, @RequestBody CustomerEntity customerEntity ) throws NoSuchCustomerFoundException {
		Optional<CustomerEntity> customer = getCustomer(id);
		if(customer.isEmpty())
			throw new NoSuchCustomerFoundException("No cus with Id:"+id);
		return customerRepository.save(customerEntity);
	}
	
	@DeleteMapping(value = "/{id}")
	public void deleteCustomer(@PathVariable Long id) throws NoSuchCustomerFoundException {
		Optional<CustomerEntity> customer = getCustomer(id);
		if(customer.isEmpty())
			throw new NoSuchCustomerFoundException("No cus with Id:"+id);
		customerRepository.delete(customer.get());
	}
}
