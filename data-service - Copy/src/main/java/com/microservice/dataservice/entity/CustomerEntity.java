package com.microservice.dataservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


import lombok.Data;

@Entity
@Table(name="customer")
@Data
public class CustomerEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	private String customerName;
	@Column
	private String customerType;
	@Column 
	private String address1;
	@Column 
	private String address2;
	@Column 
	private String city;
	@Column 
	private String state;
	@Column 
	private String postalCode;
	@Column 
	private String country;
	@Column 
	private String email;
	@Column 
	private String phone;
	@Column 
	private String fax;
	
	
	
	
	
	
	
	
}
