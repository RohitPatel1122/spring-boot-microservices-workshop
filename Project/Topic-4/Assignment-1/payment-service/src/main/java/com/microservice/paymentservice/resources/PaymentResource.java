package com.microservice.paymentservice.resources;

import java.util.ArrayList;
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
import com.microservice.paymentservice.client.PaymentServiceFeignClient;
import com.microservice.paymentservice.model.PaymentModel;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController()
@RequestMapping(value = "/payments")
public class PaymentResource {
	private static List<PaymentModel> listofPayment = new ArrayList<>();

	static {
		for (long i = 1; i <= 5; i++) {
			PaymentModel newPayment = new PaymentModel();
			newPayment.setPaymentId(i);
			listofPayment.add(newPayment);
		}
	}

	@Autowired
	Environment env;
	@Autowired
	PaymentServiceFeignClient paymentServiceFeignClient;

	@GetMapping()
	@HystrixCommand(fallbackMethod = "defaultPayments")
	public List<PaymentModel> getAllPayments() {
		System.out.println("------> Serving getAllPayments() from payment-service running on port: "
				+ env.getProperty("local.server.port"));
		return paymentServiceFeignClient.getAllPayments();
	}

	public List<PaymentModel> defaultPayments() {

		return PaymentResource.listofPayment;
	}

	public PaymentModel defaultPayment() {

		return PaymentResource.listofPayment.get(0);
	}

	@GetMapping(value = "/{id}")
	@HystrixCommand(fallbackMethod = "defaultPayment")
	public PaymentModel getPaymentById(@NotNull @PathVariable Long id) {
		System.out.println("------> Serving getPaymentById() from payment-service running on port: "
				+ env.getProperty("local.server.port"));

		return paymentServiceFeignClient.getPaymentById(id);
	}

	@GetMapping("/customer/{customerid}")
	public List<PaymentModel> getAllPaymentsForCustomer(@PathVariable(value = "customerid") Long customerid) {

		return paymentServiceFeignClient.getAllPaymentsForCustomer(customerid);
	}

	@PostMapping("")
	public PaymentModel savePayment(@RequestBody PaymentModel payment) {

		return paymentServiceFeignClient.savePayment(payment);
	}

	@PutMapping("/{id}")
	public PaymentModel updatePayment(@PathVariable Long id, @RequestBody PaymentModel updatedPayment) {

		return paymentServiceFeignClient.updatePayment(id, updatedPayment);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletePaymentById(@PathVariable Long id) {
		return paymentServiceFeignClient.deletePaymentById(id);
	}

}
