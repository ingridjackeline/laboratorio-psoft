package com.ufcg.psoft.mercadofacil.dto;


import java.util.Date;

import com.ufcg.psoft.mercadofacil.model.CarrinhoCompra;
import com.ufcg.psoft.mercadofacil.model.Usuario;


public class CompraDTO {

	private String id;
	private Date data;
	private Usuario usuario;
	private CarrinhoCompra carrinhoCompra;
	
	public CompraDTO(String id, Date data, Usuario usuario, CarrinhoCompra carrinhoCompra) {
		this.id = id;
		this.data = data;
		this.usuario = usuario;
		this.carrinhoCompra = carrinhoCompra;
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
	
}