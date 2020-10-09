package com.microservice.dataservice.resources;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.dataservice.entity.CustomerEntity;
import com.microservice.dataservice.entity.PaymentEntity;
import com.microservice.dataservice.exceptions.NoSuchCustomerFoundException;
import com.microservice.dataservice.exceptions.NoSuchPaymentFoundException;
import com.microservice.dataservice.repository.CustomerRepository;
import com.microservice.dataservice.repository.PaymentRepository;

@RestController
@RequestMapping(value = "/payments")
public class PaymentResources {

	@Autowired
	PaymentRepository paymentRepository;

	@GetMapping()
	public List<PaymentEntity> getAllPayments() {
		return paymentRepository.findAll();
	}

	@GetMapping(value = "/{id}")
	public PaymentEntity getPaymentById(@PathVariable Long id) throws NoSuchPaymentFoundException {
		return paymentRepository.findById(id)
				.orElseThrow(() -> new NoSuchPaymentFoundException("No Such Payment with id:" + id));
	}

	@PostMapping()
	public PaymentEntity savePayment(@Valid @RequestBody PaymentEntity paymentEntity)
			throws NoSuchCustomerFoundException {
		PaymentEntity savedPayment = paymentRepository.save(paymentEntity);
		return savedPayment;
	}

	@PutMapping(value = "/{id}")
	public PaymentEntity updatePayment(@PathVariable Long id, @RequestBody PaymentEntity payment)
			throws NoSuchPaymentFoundException {
		return paymentRepository.findById(id).map((paymentEntity) -> {
			paymentEntity.setAmount(payment.getAmount());
			if (StringUtils.isNotBlank(payment.getCreditType()))
				paymentEntity.setCreditType(payment.getCreditType());
			if (StringUtils.isNotBlank(payment.getDescription()))
				paymentEntity.setDescription(payment.getDescription());
			return paymentRepository.save(paymentEntity);
		}).orElseThrow(() -> new NoSuchPaymentFoundException("No Payment with Id:" + id));
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<String> deletePayment(@PathVariable Long id) throws NoSuchPaymentFoundException {
		
		return paymentRepository.findById(id).map(paymentEntity ->{
			paymentRepository.deleteById(id);
			return new ResponseEntity<>("Deleted", HttpStatus.OK) ;
		}).orElseThrow(() -> new NoSuchPaymentFoundException("No Payment with Id:" + id));
	}

}
