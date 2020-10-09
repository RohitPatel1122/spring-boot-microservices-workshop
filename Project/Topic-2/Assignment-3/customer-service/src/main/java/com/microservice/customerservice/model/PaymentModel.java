package com.microservice.customerservice.model;

import lombok.Data;

@Data
public class PaymentModel {

	private Long paymentId;

	private double amount;

	private String creditType;

	private String description;

	private CustomerModel customer;

}
