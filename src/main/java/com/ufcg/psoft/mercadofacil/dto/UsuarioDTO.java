package com.ufcg.psoft.mercadofacil.dto;


public class UsuarioDTO {

	private String cpf;
	private String nome;
	private String endereco;
	private String telefone;
	
	public UsuarioDTO(String cpf, String nome, String endereco, String telefone) {
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
	
}