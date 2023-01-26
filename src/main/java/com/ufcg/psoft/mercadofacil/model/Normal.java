package com.ufcg.psoft.mercadofacil.model;


public class Normal implements Perfil {

	private String tipoPerfil;
	
	public Normal() {
		this.tipoPerfil = "Normal";
	}
	
	@Override
	public double geraDesconto() {
		return 0;
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