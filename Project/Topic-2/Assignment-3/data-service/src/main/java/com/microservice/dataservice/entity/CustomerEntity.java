package com.microservice.dataservice.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Entity(name = "Customer")
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
	@OneToMany(mappedBy = "customer",orphanRemoval = true,cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonManagedReference
	private List<PaymentEntity> payments = new ArrayList<>();
	
	public void addPayment(PaymentEntity payment) {
		payments.add(payment);
		payment.setCustomer(this);
	}
	
	public void removePayment(PaymentEntity payment) {
		payments.remove(payment);
		payment.setCustomer(null);
	}
	
	
	
}
