package com.ufcg.psoft.mercadofacil.model;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CarrinhoCompra {

	private Map<String, List<ItemCompra>> itensCompra;
	
	public CarrinhoCompra() {
		this.itensCompra = new HashMap<String, List<ItemCompra>>();
	}
	
	public Map<String, List<ItemCompra>> getItensCompra() {
		return this.itensCompra;
	}
	
	public List<ItemCompra> getItensCompraByIdProduto(String idProduto) {
		return this.itensCompra.get(idProduto);
	}
	
	public int getQuantidadeTotalProdutos() {
		int quantidadeTotalProdutos = 0;
		
		for (List<ItemCompra> itens : this.itensCompra.values()) {
			for (ItemCompra item : itens) {
				quantidadeTotalProdutos += item.getQuantidade();
			}
		}
		
		return quantidadeTotalProdutos;
	}
	
	public boolean containsItemCompraDisponivelProduto(String idProduto) {
		boolean contains = false;
		
		if (this.itensCompra.containsKey(idProduto)) {
			List<ItemCompra> itensProduto = this.itensCompra.get(idProduto);
			
			if (itensProduto.get(itensProduto.size() - 1).containsQuantidadeDisponivel()) {
				contains = true;
			}
		}
		
		return contains;
	}
	
	public void adicionaItemCompra(String idProduto, ItemCompra itemCompra) {
		if (this.itensCompra.containsKey(idProduto)) {
			this.itensCompra.get(idProduto).add(itemCompra);
		} else {
			List<ItemCompra> itens = new ArrayList<ItemCompra>();
			itens.add(itemCompra);
			
			this.itensCompra.put(idProduto, itens);
		}
	}
	
	public void removeItensCompraProduto(String idProduto) {
		if (this.itensCompra.containsKey(idProduto)) {
			if (this.itensCompra.get(idProduto).size() == 0) {
				this.itensCompra.remove(idProduto);
			}
		}
	}
	
	public ItemCompra adicionaQuantidadeItemCompra(String idProduto) {
		List<ItemCompra> itensProduto = this.itensCompra.get(idProduto);
		ItemCompra item = itensProduto.get(itensProduto.size() - 1);
		
		item.adicionaQuantidade();

		return item;
	}
	
	public ItemCompra removeQuantidadeItemCompra(String idProduto) {
		List<ItemCompra> itensProduto = this.itensCompra.get(idProduto);
		ItemCompra item = itensProduto.get(itensProduto.size() - 1);
		
		item.removeQuantidade();
		
		if (item.getQuantidade() == 0) {
			this.itensCompra.get(idProduto).remove(itensProduto.size() - 1);
			this.removeItensCompraProduto(idProduto);
		}
		
		return item;
	}
	
	public double calculaValorTotal() {
		double valorTotal = 0;
		
		for (List<ItemCompra> itens : this.itensCompra.values()) {
			for (ItemCompra item : itens) {
				valorTotal += item.calculaSubTotal();
			}
		}
		
		return valorTotal;
	}
	
	public String toString() {
		String compras = "";
		
		for (List<ItemCompra> itens : this.itensCompra.values()) {
			for (ItemCompra item : itens) {
				compras += item.toString() + "\n";
			}
			
			compras += "\n";
		}
		
		compras += "Valor Total: " + this.calculaValorTotal() + " reais";
		
		return compras;
	}
	
}