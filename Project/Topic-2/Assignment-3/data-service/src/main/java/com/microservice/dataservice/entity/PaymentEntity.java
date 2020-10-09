package com.microservice.dataservice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

@Entity(name = "Payment")
@Table(name="payment")
@Data
public class PaymentEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long paymentId;
	
	@Column
	private double amount;
	
	@Column
	private String creditType;
	
	@Column
	private String description;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id")
	
	@JsonBackReference
	private CustomerEntity customer;
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PaymentEntity )) return false;
        return this.paymentId != null && this.paymentId.equals(((PaymentEntity) o).getPaymentId());
    }
 
    @Override
    public int hashCode() {
        return 31;
    }
}
