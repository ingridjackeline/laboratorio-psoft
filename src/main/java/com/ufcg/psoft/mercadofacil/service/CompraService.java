package com.ufcg.psoft.mercadofacil.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.mercadofacil.exception.BatchNotFoundException;
import com.ufcg.psoft.mercadofacil.exception.PaymentMethodNotAcceptedException;
import com.ufcg.psoft.mercadofacil.exception.ProductNotFoundException;
import com.ufcg.psoft.mercadofacil.exception.PurchaseCannotBeAcceptedException;
import com.ufcg.psoft.mercadofacil.exception.PurchaseNotFoundException;
import com.ufcg.psoft.mercadofacil.exception.UserNotFoundException;
import com.ufcg.psoft.mercadofacil.model.CarrinhoCompra;
import com.ufcg.psoft.mercadofacil.model.Compra;
import com.ufcg.psoft.mercadofacil.model.ItemCompra;
import com.ufcg.psoft.mercadofacil.model.Lote;
import com.ufcg.psoft.mercadofacil.model.Pagamento;
import com.ufcg.psoft.mercadofacil.model.Produto;
import com.ufcg.psoft.mercadofacil.model.Usuario;
import com.ufcg.psoft.mercadofacil.repository.CompraRepository;


@Service
public class CompraService {
	
	@Autowired
	private CompraRepository compraRepository;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private LoteService loteService;
	
	@Autowired
	private ProdutoService produtoService;
	
	public void adicionaProduto(String cpfUsuario, String idProduto) throws UserNotFoundException, ProductNotFoundException, BatchNotFoundException {
		Usuario usuario = this.usuarioService.getUsuarioByCpf(cpfUsuario);
		Produto produto = this.produtoService.getProdutoById(idProduto);
		
		CarrinhoCompra carrinhoUsuario = usuario.getCarrinho();
		
		ItemCompra item = null;
		
		if (carrinhoUsuario.containsItemCompraDisponivelProduto(idProduto)) {
			item = carrinhoUsuario.adicionaQuantidadeItemCompra(idProduto);
		} else {
			Lote loteProduto = this.loteService.getLoteDisponivelProdutoComMenorPrazoValidade(idProduto);
			item = new ItemCompra(produto, loteProduto);
			
			carrinhoUsuario.adicionaItemCompra(idProduto, item);
		}
		
		this.loteService.removeQuantidadeProdutosLote(item.getLoteProduto().getId(), 1);
	}
	
	public void removeProduto(String cpfUsuario, String idProduto) throws UserNotFoundException, ProductNotFoundException, BatchNotFoundException {
		Usuario usuario = this.usuarioService.getUsuarioByCpf(cpfUsuario);
		Produto produto = this.produtoService.getProdutoById(idProduto);
		
		CarrinhoCompra carrinho = usuario.getCarrinho();
		
		if (carrinho.getItensCompraByIdProduto(produto.getId()) == null) {
			throw new ProductNotFoundException("Produto: " + idProduto + " não encontrado no carrinho");
		}
		
		ItemCompra item = carrinho.removeQuantidadeItemCompra(produto.getId());
		
		if (carrinho.getItensCompra().size() == 0) {
			usuario.removeCarrinho();
		}
		
		this.loteService.adicionaQuantidadeProdutosLote(item.getLoteProduto().getId(), 1);
	}
	
	public String finalizaCompra(String cpfUsuario, String formaPagamento) throws UserNotFoundException, PurchaseCannotBeAcceptedException, PaymentMethodNotAcceptedException {
		Usuario usuario = this.usuarioService.getUsuarioByCpf(cpfUsuario);

		CarrinhoCompra carrinho = usuario.getCarrinho();
		
		if (carrinho.getItensCompra().size() == 0) {
			throw new PurchaseCannotBeAcceptedException("A compra não pode ser realizada");
		}
		
		Pagamento pagamento = this.compraRepository.getFormaPagamento(formaPagamento);
		
		if (pagamento == null) {
			throw new PaymentMethodNotAcceptedException("Forma de pagamento não disponível");
		}
		
		Compra compra = new Compra(usuario, carrinho, pagamento);
		
		this.compraRepository.addCompraUsuario(cpfUsuario, compra);
		
		usuario.removeCarrinho();
		
		return compra.toString();
	}
	
	public void descartaCompra(String cpfUsuario) throws UserNotFoundException, BatchNotFoundException {
		Usuario usuario = this.usuarioService.getUsuarioByCpf(cpfUsuario);

		CarrinhoCompra carrinho = usuario.getCarrinho();
		
		Map<String, List<ItemCompra>> itensCompra = carrinho.getItensCompra();
			
		for (List<ItemCompra> itens : itensCompra.values()) {
			for (ItemCompra item : itens) {
				this.loteService.adicionaQuantidadeProdutosLote(item.getLoteProduto().getId(), item.getQuantidade());
			}
		}
		
		usuario.removeCarrinho();
	}
	
	public List<Compra> listaCompras(String cpfUsuario) throws UserNotFoundException {
		Usuario usuario = this.usuarioService.getUsuarioByCpf(cpfUsuario);

		Map<String, Compra> comprasUsuario = this.compraRepository.getComprasByIdUsuario(usuario.getCpf());
		List<Compra> compras = new ArrayList<Compra>();
		
		if (comprasUsuario != null) {
			for (Compra compra : comprasUsuario.values()) {
				compras.add(compra);
			}
		}
		
		return compras;
	}
	
	public Compra exibeDetalhesCompra(String cpfUsuario, String idCompra) throws PurchaseNotFoundException, UserNotFoundException {
		Usuario usuario = this.usuarioService.getUsuarioByCpf(cpfUsuario);
		Compra compra = this.compraRepository.getCompraUsuario(usuario.getCpf(), idCompra);
		
		this.verificaCompra(compra, idCompra);
				
		return compra;
	}
	
	public String listaFormasPagamento() {	
		String formasPagamento = "Formas de pagamento disponíveis:" + "\n" + "\n";
		
		for (String forma : this.compraRepository.listaFormasPagamento()) {
			formasPagamento += "- " + forma + "\n";
		}
		
		return formasPagamento.trim();
	}
	
	private void verificaCompra(Compra compra, String id) throws PurchaseNotFoundException {
		if (compra == null) {
			throw new PurchaseNotFoundException("Compra: " + id + " não encontrada");
		}
	}

}