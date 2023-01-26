package com.ufcg.psoft.mercadofacil.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.ufcg.psoft.mercadofacil.dto.ProdutoDTO;
import com.ufcg.psoft.mercadofacil.exception.ProductNotFoundException;
import com.ufcg.psoft.mercadofacil.model.Produto;
import com.ufcg.psoft.mercadofacil.service.ProdutoService;


@RestController
@RequestMapping("/api")
@CrossOrigin
public class ProdutoController {
	
	@Autowired
	private ProdutoService produtoService;
	
	@RequestMapping(value = "/produto/", method = RequestMethod.POST)
	public ResponseEntity<?> criarProduto(@RequestBody ProdutoDTO produtoDTO, UriComponentsBuilder ucBuilder) {
		String prodID = this.produtoService.addProduto(produtoDTO);
		
		return new ResponseEntity<String>("Produto cadastrado com ID:" + prodID, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/produto/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> consultarProduto(@PathVariable("id") String id) {
		Produto produto;
		
		try {
			produto = this.produtoService.getProdutoById(id);
		} catch (ProductNotFoundException e) {
			return new ResponseEntity<String>("Produto não encontrado", HttpStatus.NOT_FOUND);
		}
			
		return new ResponseEntity<Produto>(produto, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/produto/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> atualizarProduto(@PathVariable("id") String id, @RequestBody ProdutoDTO produtoDTO, UriComponentsBuilder ucBuilder) {
		try {
			this.produtoService.editProd(id, produtoDTO);
		} catch (ProductNotFoundException e) {
			return new ResponseEntity<String>("Produto não encontrado", HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<String>("Produto com ID " + id + " atualizado", HttpStatus.OK);
	}
	
	@RequestMapping(value = "/produto/{id}", method = RequestMethod.DELETE) 
	public ResponseEntity<?> removerProduto(@PathVariable("id") String id) {
		try {
			this.produtoService.delProd(id);
		} catch (ProductNotFoundException e) {
			return new ResponseEntity<String>("Produto não encontrado", HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<String>("Produto com ID " + id + " removido", HttpStatus.OK);
	}
	
	@RequestMapping(value = "/produtos", method = RequestMethod.GET)
	public ResponseEntity<?> listarProdutos(@RequestParam(value = "lote", required = false) boolean lote, @RequestParam(value = "name", required = false) String nome) {
		List<Produto> produtos = new ArrayList<Produto>();
		
		if (lote == true) {
			if (nome != null) {
				produtos = this.produtoService.listarProdsLoteByName(nome);
			} else {
				produtos = this.produtoService.listarProdutosComLote();
			}
		} else {
			if (nome != null) {
				produtos = this.produtoService.listarProdsByName(nome);
			} else {
				produtos = this.produtoService.listarProdutos();
			}
		}
		
		return new ResponseEntity<List<Produto>>(produtos, HttpStatus.OK);
	}
	
}