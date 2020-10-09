package com.microservice.dataservice.exceptions;

import lombok.Data;

@Data
public class NoSuchCustomerFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1979082840478124200L;

	public NoSuchCustomerFoundException(String msg) {
		super(msg);
	}

}
