package com.ufcg.psoft.mercadofacil.model;


public class PayPal extends Pagamento {

	public PayPal() {
		super("PayPal", 0.02);
	}

	@Override
	public double calculaAcrescimo(double valorCompra) {
		return valorCompra * 0.02;
	}
	
}