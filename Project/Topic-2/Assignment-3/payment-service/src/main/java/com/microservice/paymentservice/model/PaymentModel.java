package com.microservice.paymentservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentModel {

private Long paymentId;
	
	
	private double amount;
	
	
	private String creditType;
	
	
	private String description;
	
	private CustomerModel customer;

}
