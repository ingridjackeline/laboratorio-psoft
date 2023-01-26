package com.ufcg.psoft.mercadofacil.model;


public class Especial implements Perfil {
	
	private String tipoPerfil;
	
	public Especial() {
		this.tipoPerfil = "Especial";
	}

	@Override
	public double geraDesconto() {
		return 0.1;
	}

	@Override
	public String toString() {
		return "Tipo de perfil: " + this.tipoPerfil;
	}
	
	@Override
	public String getTipoPerfil() {
		return this.tipoPerfil;
	}
	
}