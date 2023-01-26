package com.ufcg.psoft.mercadofacil.repository;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ufcg.psoft.mercadofacil.dto.LoteDTO;
import com.ufcg.psoft.mercadofacil.model.Lote;
import com.ufcg.psoft.mercadofacil.model.Produto;


@Repository
public class LoteRepository {
	
	private Map<String, Lote> lotes;
	
	public LoteRepository() {
		this.lotes = new HashMap<String, Lote>();
	}
	
	public Collection<Lote> getAll() {
		return this.lotes.values();
	}
	
	public Lote getLote(String id) {
		return this.lotes.get(id);
	}
	
	public void delLot(String id) {
		this.lotes.remove(id);
	}
	
	public void editLote(Lote lote, LoteDTO loteDTO, Produto produto) {
		lote.setProduto(produto);
		lote.setQuantidade(loteDTO.getQuantidade());
		
		this.lotes.replace(lote.getId(), lote);
	}
	
	public String addLote(Lote lote) {
		this.lotes.put(lote.getId(), lote);
		
		return lote.getId();
	}

}