package com.ufcg.psoft.mercadofacil.model;


public class ItemCompra {

	private Produto produto;
	private Lote loteProduto;
	private int quantidade;
	
	public ItemCompra(Produto produto, Lote loteProduto) {
		this.produto = produto;
		this.loteProduto = loteProduto;
		this.quantidade = 1;
	}
	
	public Produto getProduto() {
		return this.produto;
	}
	
	public int getQuantidade() {
		return this.quantidade;
	}
	
	public Lote getLoteProduto() {
		return this.loteProduto;
	}
	
	public boolean containsQuantidadeDisponivel() {
		return this.loteProduto.getQuantidade() > 0;
	}
	
	public void adicionaQuantidade() {
		this.quantidade += 1;
	}
	
	public void removeQuantidade() {
		this.quantidade -= 1;
	}
	
	public double calculaSubTotal() {
		return this.produto.getPreco() * this.quantidade;
	}
	
	public String toString() {
		return this.getProduto().toString() + " - Lote ID: " + this.getLoteProduto().getId() + 
				"\n" + "Quantidade: " + this.getQuantidade() + " - SubTotal: " + this.calculaSubTotal() + " reais";
	}
	
}