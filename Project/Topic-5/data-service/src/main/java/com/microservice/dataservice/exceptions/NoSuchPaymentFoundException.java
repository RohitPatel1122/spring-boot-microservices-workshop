package com.microservice.dataservice.exceptions;

import lombok.Data;

@Data
public class NoSuchPaymentFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1979082840478124200L;

	public NoSuchPaymentFoundException(String msg) {
		super(msg);
	}

}
