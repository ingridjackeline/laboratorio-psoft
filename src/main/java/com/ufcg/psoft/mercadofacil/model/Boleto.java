package com.ufcg.psoft.mercadofacil.model;


public class Boleto extends Pagamento {
	
	public Boleto() {
		super("Boleto", 0);
	}

	@Override
	public double calculaAcrescimo(double valorCompra) {
		return valorCompra * 0;
	}
	
}