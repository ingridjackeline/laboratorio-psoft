package com.ufcg.psoft.mercadofacil.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.mercadofacil.dto.LoteDTO;
import com.ufcg.psoft.mercadofacil.exception.BatchNotFoundException;
import com.ufcg.psoft.mercadofacil.exception.ProductNotFoundException;
import com.ufcg.psoft.mercadofacil.model.Lote;
import com.ufcg.psoft.mercadofacil.model.Produto;
import com.ufcg.psoft.mercadofacil.repository.LoteRepository;
import com.ufcg.psoft.mercadofacil.repository.ProdutoRepository;


@Service
public class LoteService {

	@Autowired
	private LoteRepository loteRep;
	
	@Autowired
	private ProdutoRepository produtoRep;
		
	public String addLote(LoteDTO loteDTO) throws ProductNotFoundException {
		Produto prod = this.produtoRep.getProd(loteDTO.getIdProduto());
		
		this.verificaProduto(prod, loteDTO.getIdProduto());
		
		Lote lote = new Lote(prod, loteDTO.getQuantidade(), loteDTO.getDataFabricacao(), loteDTO.getDataValidade());
		this.loteRep.addLote(lote);

		return lote.getId();
	}
	
	public Lote getLoteById(String id) throws BatchNotFoundException {
		Lote lote = this.loteRep.getLote(id);
		
		this.verificaLote(lote, id);
				
		return lote;
	}
	
	public void editLote(String id, LoteDTO loteDTO) throws BatchNotFoundException, ProductNotFoundException {
		Lote lote = this.loteRep.getLote(id);
		Produto produto = this.produtoRep.getProd(loteDTO.getIdProduto());
		
		this.verificaLote(lote, id);
		this.verificaProduto(produto, loteDTO.getIdProduto());
		
		this.loteRep.editLote(lote, loteDTO, produto);
	}
	
	public void delLote(String id) throws BatchNotFoundException {
		Lote lote = this.loteRep.getLote(id);
		
		this.verificaLote(lote, id);
		
		this.loteRep.delLot(id);
	}
	
	public List<Lote> listarLotes() {
		return new ArrayList<Lote>(loteRep.getAll());
	}
	
	public void adicionaQuantidadeProdutosLote(String id, int quantidade) throws BatchNotFoundException {
		Lote lote = this.loteRep.getLote(id);
		
		this.verificaLote(lote, id);
		
		lote.adicionaQuantidade(quantidade);
	}
	
	public void removeQuantidadeProdutosLote(String id, int quantidade) throws BatchNotFoundException {
		Lote lote = this.loteRep.getLote(id);
		
		this.verificaLote(lote, id);
		
		lote.removeQuantidade(quantidade);
	}
	
	public Lote getLoteDisponivelProdutoComMenorPrazoValidade(String idProduto) throws ProductNotFoundException, BatchNotFoundException {
		Produto produto = this.produtoRep.getProd(idProduto);
		
		this.verificaProduto(produto, idProduto);
		
		List<Lote> lotesProduto = this.getLotesDisponiveisProduto(produto);
		
		if (lotesProduto.size() == 0) {
			throw new BatchNotFoundException("Não há lote do produto no sistema");
		}
		
		Lote loteDisponivelComMenorPrazo = lotesProduto.get(0);
		
		for (Lote lote : lotesProduto) {
			if (lote.getDataValidade().before(loteDisponivelComMenorPrazo.getDataValidade())) {
				loteDisponivelComMenorPrazo = lote;
			}
		}
		
		return loteDisponivelComMenorPrazo;
	}
	
	private List<Lote> getLotesDisponiveisProduto(Produto produto) {
		List<Lote> lotesProduto = new ArrayList<Lote>();
		
		for (Lote lote : this.loteRep.getAll()) {
			if (lote.getProduto().equals(produto) && lote.getQuantidade() > 0) {
				lotesProduto.add(lote);
			}
		}
		
		return lotesProduto;
	}
	
	private void verificaProduto(Produto produto, String id) throws ProductNotFoundException {
		if (produto == null) {
			throw new ProductNotFoundException("Produto: " + id + " não encontrado");
		}
	}
	
	private void verificaLote(Lote lote, String id) throws BatchNotFoundException {
		if (lote == null) {
			throw new BatchNotFoundException("Lote: " + id + " não encontrado");
		}
	}
	
}