package com.ufcg.psoft.mercadofacil.model;


public class Usuario {

	private String cpf;
	private String nome;
	private String endereco;
	private String telefone;
	private CarrinhoCompra carrinhoCompra;
	private Perfil perfil;
	
	public Usuario(String cpf, String nome, String endereco, String telefone) {
		this.cpf = cpf;
		this.nome = nome;
		this.endereco = endereco;
		this.telefone = telefone;
	}
	
	public String getCpf() {
		return this.cpf;
	}
	
	public String getNome() {
		return this.nome;
	}
	
	public String getEndereco() {
		return this.endereco;
	}
	
	public String getTelefone() {
		return this.telefone;
	}
	
	public CarrinhoCompra getCarrinho() {
		if (this.carrinhoCompra == null) {
			this.carrinhoCompra = new CarrinhoCompra();
		}
		
		return this.carrinhoCompra;
	}
	
	public Perfil getPerfil() {
		return this.perfil;
	}
	
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	public boolean alteraPerfil(String perfil) {
		if (perfil == null || perfil.toLowerCase().equals("normal")) {
			this.perfil = new Normal();
		} else if (perfil.toLowerCase().equals("especial")) {
			this.perfil = new Especial();
		} else if (perfil.toLowerCase().equals("premium")) {
			this.perfil = new Premium();
		} else {
			return false;
		}
		
		return true;
	}
	
	public void removeCarrinho() {
		this.carrinhoCompra = null;
	}
		
	public String toString() {
		return "CPF do Usuário: " + this.getCpf() + " - Nome: " + this.getNome() + " " + this.perfil.toString(); 
	}
	
}