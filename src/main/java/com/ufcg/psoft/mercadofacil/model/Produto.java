package com.ufcg.psoft.mercadofacil.model;


import java.util.UUID;


public class Produto {
	
	private String id;
	private String nome; 
	private String fabricante;
	private double preco;

	public Produto(String nome, String fabricante, double preco) {
		this.id = UUID.randomUUID().toString();
		this.nome = nome;
		this.fabricante = fabricante;
		this.preco = preco;
	}
		
	public String getId() {
		return this.id;
	}

	public String getNome() {
		return this.nome;
	}

	public String getFabricante() {
		return this.fabricante;
	}
	
	public double getPreco() {
		return this.preco;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public void setFabricante(String fabricante) {
		this.fabricante = fabricante;
	}
	
	public void setPreco(double preco) {
		this.preco = preco;
	}
	
	public String toString() {
		return "Produto: " + this.getNome() + " - Fabricante: " + this.getFabricante();
	}
	
}