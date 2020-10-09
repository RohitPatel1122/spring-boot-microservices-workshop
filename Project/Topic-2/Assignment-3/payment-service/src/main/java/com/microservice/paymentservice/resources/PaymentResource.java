package com.microservice.paymentservice.resources;

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

import com.microservice.paymentservice.model.CustomerModel;
import com.microservice.paymentservice.model.PaymentModel;

@RestController()
@RequestMapping(value = "/payments")
public class PaymentResource {

	@Autowired
	RestTemplate restTemplate;
	@Autowired
	Environment env;
	
	@GetMapping()
	public PaymentModel[] getAllPayments() {
		System.out.println("------> Serving getAllPayments() from payment-service running on port: "+env.getProperty("local.server.port"));
		PaymentModel[] response = restTemplate.getForObject("http://data-service/payments/", PaymentModel[].class);
		return response;
	}

	@GetMapping(value = "/{id}")
	public PaymentModel getPaymentById(@NotNull @PathVariable Long id) {
		System.out.println("------> Serving getPaymentById() from payment-service running on port: "+env.getProperty("local.server.port"));
		PaymentModel paymentModel = restTemplate.getForObject("http://data-service/payments/" + id,
				PaymentModel.class);
		return paymentModel;
	}

	@GetMapping("/customer/{customerid}")
	public PaymentModel[] getAllPaymentsForCustomer(@PathVariable Long id) {
		PaymentModel[] payments = restTemplate.getForObject("http://data-service/payments/customer/" + id,
				PaymentModel[].class);
		return payments;
	}

	@PostMapping("/customer/{customerid}")
	public ResponseEntity<PaymentModel> savePayment(@PathVariable Long customerid, @RequestBody PaymentModel payment) {
		CustomerModel customerModel = restTemplate.getForObject("http://data-service/customers/" + customerid,
				CustomerModel.class);
		payment.setCustomer(customerModel);
		PaymentModel savedpayment = restTemplate.postForObject("http://data-service/payments/", payment,
				PaymentModel.class);
		savedpayment.setCustomer(customerModel);
		return new ResponseEntity<PaymentModel>(savedpayment, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<PaymentModel> updatePayment(@PathVariable Long id, @RequestBody PaymentModel updatedPayment) {
		HttpEntity<PaymentModel> httpEntity = new HttpEntity<PaymentModel>(updatedPayment);
		return restTemplate.exchange("http://data-service/payments/" + id, HttpMethod.PUT, httpEntity,
				PaymentModel.class);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletePaymentById(@PathVariable Long id) {
		return restTemplate.exchange("http://data-service/payments/" + id, HttpMethod.DELETE, null, String.class);
	}

}
