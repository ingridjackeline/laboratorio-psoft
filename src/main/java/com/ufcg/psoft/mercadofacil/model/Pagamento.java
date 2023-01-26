package com.ufcg.psoft.mercadofacil.model;


import java.util.UUID;


public abstract class Pagamento {

	private String id;
	private String formaPagamento;
	private double acrescimo;
	
	public Pagamento(String formaPagamento, double acrescimo) {
		this.id = UUID.randomUUID().toString();
		this.formaPagamento = formaPagamento;
		this.acrescimo = acrescimo;
	}
	
	public String getId() {
		return this.id;
	}
	
	public String getFormaPagamento() {
		return this.formaPagamento;
	}
	
	public double getAcrescimo() {
		return this.acrescimo;
	}
	
	public abstract double calculaAcrescimo(double valorCompra);
	
	public String toString() {
		return "Forma de Pagamento: " + this.formaPagamento;
	}
	
}