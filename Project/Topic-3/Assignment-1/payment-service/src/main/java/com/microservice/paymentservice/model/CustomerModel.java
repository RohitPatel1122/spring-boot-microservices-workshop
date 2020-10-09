package com.microservice.paymentservice.model;

import java.util.ArrayList;
import java.util.List;


import lombok.Data;

@Data
public class CustomerModel {

	private Long id;
	
	private String customerName;
	
	private String customerType;
	 
	private String address1;
	 
	private String address2;
	 
	private String city;
	 
	private String state;
	 
	private String postalCode;
	 
	private String country;
	 
	private String email;
	 
	private String phone;
	 
	private String fax;
	
}
