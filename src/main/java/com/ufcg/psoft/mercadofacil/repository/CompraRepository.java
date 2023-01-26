package com.ufcg.psoft.mercadofacil.repository;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ufcg.psoft.mercadofacil.model.Boleto;
import com.ufcg.psoft.mercadofacil.model.CartaoCredito;
import com.ufcg.psoft.mercadofacil.model.Compra;
import com.ufcg.psoft.mercadofacil.model.Pagamento;
import com.ufcg.psoft.mercadofacil.model.PayPal;


@Repository
public class CompraRepository {

	private Map<String, Map<String, Compra>> compras;
	private Map<String, Pagamento> formasPagamento;
	
	public CompraRepository() {
		this.compras = new HashMap<String, Map<String, Compra>>();
		this.formasPagamento = new HashMap<String, Pagamento>();
		
		this.adicionaFormasPagamento();
	}
	
	public Map<String, Map<String, Compra>> getAll() {
		return this.compras;
	}
	
	public Pagamento getFormaPagamento(String formaPagamento) {
		Pagamento forma = null;
		
		if (formaPagamento == null) {
			forma = new Boleto();
		} else {
			for (Pagamento pagamento : this.formasPagamento.values()) {
				if (pagamento.getFormaPagamento().toLowerCase().equals(formaPagamento.toLowerCase())) {
					forma = pagamento;
				}
			}
		}
		
		return forma;
	}
	
	public Map<String, Compra> getComprasByIdUsuario(String idUsuario) {
		return this.compras.get(idUsuario);
	}
	
	public Compra getCompraUsuario(String idUsuario, String idCompra) {
		Map<String, Compra> comprasUsuario = this.compras.get(idUsuario);
		Compra compraUsuario = comprasUsuario.get(idCompra);
		
		return compraUsuario;
	}
	
	public String addCompraUsuario(String idUsuario, Compra compra) {
		if (this.compras.containsKey(idUsuario)) {
			this.compras.get(idUsuario).put(compra.getId(), compra);
		} else {
			Map<String, Compra> comprasUsuario = new HashMap<String, Compra>();
			comprasUsuario.put(compra.getId(), compra);
			this.compras.put(idUsuario, comprasUsuario);
		}
		
		return compra.getId();
	}
	
	public List<String> listaFormasPagamento() {
		List<String> formasPagamento = new ArrayList<String>();
		
		for (Pagamento formaPagamento : this.formasPagamento.values()) {
			formasPagamento.add(formaPagamento.getFormaPagamento());
		}
		
		return formasPagamento;
	}
	
	private void adicionaFormasPagamento() {
		Pagamento boleto = new Boleto();
		Pagamento paypal = new PayPal();
		Pagamento cartaoCredito = new CartaoCredito();
		
		this.formasPagamento.put(boleto.getId(), boleto);
		this.formasPagamento.put(paypal.getId(), paypal);
		this.formasPagamento.put(cartaoCredito.getId(), cartaoCredito);
	}
	
}