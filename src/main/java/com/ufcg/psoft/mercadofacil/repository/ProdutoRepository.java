package com.ufcg.psoft.mercadofacil.repository;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ufcg.psoft.mercadofacil.dto.ProdutoDTO;
import com.ufcg.psoft.mercadofacil.model.Produto;


@Repository
public class ProdutoRepository {
	
	private Map<String, Produto> produtos;
	
	public ProdutoRepository() {
		this.produtos = new HashMap<String, Produto>();
	}

	public Collection<Produto> getAll() {
		return this.produtos.values();
	}
	
	public Produto getProd(String id) {
		return this.produtos.get(id);
	}
	
	public void delProd(String id) {
		this.produtos.remove(id);
	}
	
	public void editProd(Produto prod, ProdutoDTO prodDTO) {
		prod.setNome(prodDTO.getNome());
		prod.setFabricante(prodDTO.getFabricante());
		prod.setPreco(prodDTO.getPreco());
		
		this.produtos.replace(prod.getId(), prod);
	}
	
	public String addProduto(Produto prod) {
		this.produtos.put(prod.getId(), prod);
		
		return prod.getId();
	}
	
}