package com.ufcg.psoft.mercadofacil.dto;


import java.util.Date;


public class LoteDTO {
	
	private String idProduto;
	private int quantidade;
	private Date dataFabricacao;
	private Date dataValidade;
	
	public LoteDTO(String idProduto, int quantidade, Date dataFabricacao, Date dataValidade) {
		this.idProduto = idProduto;
		this.quantidade = quantidade;
		this.dataFabricacao = dataFabricacao;
		this.dataValidade = dataValidade;
	}
	
	public String getIdProduto() {
		return this.idProduto;
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
	
}