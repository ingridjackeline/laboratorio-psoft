package com.ufcg.psoft.mercadofacil.model;


import java.util.Date;
import java.util.UUID;


public class Lote {
	
	private String id;
	private Produto produto;
	private int quantidade; 
	private Date dataFabricacao;
	private Date dataValidade; 
	
	public Lote(Produto produto, int quantidade, Date dataFabricacao, Date dataValidade) {
		this.id = UUID.randomUUID().toString();
		this.produto = produto;
		this.quantidade = quantidade;
		this.dataFabricacao = dataFabricacao;
		this.dataValidade = dataValidade;
	}
	
	public String getId() {
		return this.id;
	}

	public Produto getProduto() {
		return this.produto;
	}

	public int getQuantidade() {
		return this.quantidade;
	}

	public Date getDataFabricacao() {
		return this.dataFabricacao;
	}

	public Date getDataValidade() {
		return this.dataValidade;
	}
	
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	
	public void adicionaQuantidade(int quantidade) {
		this.quantidade += quantidade;
	}
	
	public void removeQuantidade(int quantidade) {
		this.quantidade -= quantidade;
	}

	public String toString() {
		return "Lote ID: " + this.getId() + " - Produto: " + this.getProduto().getNome() + " - " + this.getQuantidade() + " itens";
	}
	
}