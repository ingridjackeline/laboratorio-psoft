package com.ufcg.psoft.mercadofacil.model;


public class CartaoCredito extends Pagamento {

	public CartaoCredito() {
		super("Cartão de Crédito", 0.05);
	}

	@Override
	public double calculaAcrescimo(double valorCompra) {
		return valorCompra * 0.05;
	}
	
}