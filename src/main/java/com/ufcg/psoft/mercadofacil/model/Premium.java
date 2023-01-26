package com.ufcg.psoft.mercadofacil.model;


public class Premium implements Perfil {

	private String tipoPerfil;
	
	public Premium() {
		this.tipoPerfil = "Premium";
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