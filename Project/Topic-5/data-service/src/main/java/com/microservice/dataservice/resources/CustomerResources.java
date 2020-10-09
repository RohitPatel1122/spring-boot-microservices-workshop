package com.microservice.dataservice.resources;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.dataservice.entity.CustomerEntity;
import com.microservice.dataservice.entity.PaymentEntity;
import com.microservice.dataservice.exceptions.NoSuchCustomerFoundException;
import com.microservice.dataservice.exceptions.NoSuchPaymentFoundException;
import com.microservice.dataservice.repository.CustomerRepository;
import com.microservice.dataservice.repository.PaymentRepository;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/customersdata")
@Slf4j
public class CustomerResources {

	@Autowired
	private Environment env;
	@Autowired
	CustomerRepository customerRepository;

	@GetMapping()
	public List<CustomerEntity> getAllCustomers() {
		log.debug("------> Service running on port: " + env.getProperty("local.server.port"));
		return customerRepository.findAll();
	}

	@GetMapping(value = "/{id}")
	public CustomerEntity getCustomerById(@PathVariable Long id) throws NoSuchCustomerFoundException {

		return customerRepository.findById(id)
				.orElseThrow(() -> new NoSuchCustomerFoundException("No Customer with Id: " + id));
	}

	@PostMapping()
	public CustomerEntity saveCustomer(@Valid @RequestBody CustomerEntity customerEntity) {
		return customerRepository.save(customerEntity);
	}

	@PutMapping(value = "/{id}")
	public CustomerEntity updateCustomer(@PathVariable Long id, @Valid @RequestBody CustomerEntity customer)
			throws NoSuchCustomerFoundException {
		return customerRepository.findById(id).map((customerEntity) -> {
			if (StringUtils.isNotBlank(customer.getAddress1()))
				customerEntity.setAddress1(customer.getAddress1());
			if (StringUtils.isNotBlank(customer.getAddress2()))
				customerEntity.setAddress2(customer.getAddress2());
			if (StringUtils.isNotBlank(customer.getCity()))
				customerEntity.setCity(customer.getCity());
			if (StringUtils.isNotBlank(customer.getCountry()))
				customerEntity.setCountry(customer.getCountry());
			if (StringUtils.isNotBlank(customer.getCustomerName()))
				customerEntity.setCustomerName(customer.getCustomerName());
			if (StringUtils.isNotBlank(customer.getCustomerType()))
				customerEntity.setCustomerType(customer.getCustomerType());
			if (StringUtils.isNotBlank(customer.getEmail()))
				customerEntity.setEmail(customer.getEmail());
			if (StringUtils.isNotBlank(customer.getPhone()))
				customerEntity.setPhone(customer.getPhone());
			return customerRepository.save(customerEntity);
		}).orElseThrow(() -> new NoSuchCustomerFoundException("No Customer with Id: " + id));

	}

	@DeleteMapping(value = "/{id}")
	public void deleteCustomer(@PathVariable Long id) throws NoSuchCustomerFoundException {
		CustomerEntity customerEntity = customerRepository.findById(id)
				.orElseThrow(() -> new NoSuchCustomerFoundException("No Customer with Id: " + id));
		customerRepository.delete(customerEntity);
	}
}
