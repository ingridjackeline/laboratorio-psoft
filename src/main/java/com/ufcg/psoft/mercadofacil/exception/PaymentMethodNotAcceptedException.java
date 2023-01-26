package com.ufcg.psoft.mercadofacil.exception;


public class PaymentMethodNotAcceptedException extends Exception {

	private static final long serialVersionUID = 1L;

	public PaymentMethodNotAcceptedException(String msg) {
		super(msg);
	}
	
}