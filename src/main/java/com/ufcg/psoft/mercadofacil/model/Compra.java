package com.ufcg.psoft.mercadofacil.model;


import java.util.Date;
import java.util.UUID;


public class Compra {

	private String id;
	private Date data;
	private Usuario usuario;
	private CarrinhoCompra carrinhoCompra;
	private Pagamento formaPagamento;
	
	public Compra(Usuario usuario, CarrinhoCompra carrinhoCompra, Pagamento formaPagamento) {
		this.id = UUID.randomUUID().toString();
		this.data = new Date();
		this.usuario = usuario;
		this.carrinhoCompra = carrinhoCompra;
		this.formaPagamento = formaPagamento;
	}
	
	public String getId() {
		return this.id;
	}
	
	public Date getData() {
		return this.data;
	}
	
	public Usuario getUsuario() {
		return this.usuario;
	}
	
	public CarrinhoCompra getCarrinhoCompra() {
		return this.carrinhoCompra;
	}
	
	public Pagamento getFormaPagamento() {
		return this.formaPagamento;
	}
	
	private double calculaValorTotalComDesconto() {
		String perfilUsuario = this.usuario.getPerfil().getTipoPerfil();
		double valorTotal = this.getCarrinhoCompra().calculaValorTotal();
		double valorTotalComDesconto = valorTotal;

		if (perfilUsuario.equals("Especial") && this.carrinhoCompra.getQuantidadeTotalProdutos() > 10) {
			valorTotalComDesconto -= valorTotal * this.usuario.getPerfil().geraDesconto();
		} else if (perfilUsuario.equals("Premium") && this.carrinhoCompra.getQuantidadeTotalProdutos() > 5) {
			valorTotalComDesconto -= valorTotal * this.usuario.getPerfil().geraDesconto();
		}
		
		return valorTotalComDesconto;
	}
	
	private double calculaValorTotalComAcrescimo() {
		double valorTotal = this.calculaValorTotalComDesconto();
		double valorTotalComAcrescimo = valorTotal + this.getFormaPagamento().calculaAcrescimo(valorTotal);
		
		return valorTotalComAcrescimo;
	}
	
	public String toString() {
		return "Compra ID: " + this.getId() + " - Data: " + this.getData() + "\n" + this.getUsuario().toString() + 
				"\n" + "\n" + "Produtos: " + "\n" + this.getCarrinhoCompra().toString() + "\n" + "\n" + 
				"Forma de pagamento: " + this.getFormaPagamento().getFormaPagamento() + " - Valor de desconto: " + 
				this.getUsuario().getPerfil().geraDesconto() + " - Valor de acréscimo: " + this.getFormaPagamento().getAcrescimo() + 
				"\n" + "Valor final pago: " + this.calculaValorTotalComAcrescimo() + " reais";
	}

}