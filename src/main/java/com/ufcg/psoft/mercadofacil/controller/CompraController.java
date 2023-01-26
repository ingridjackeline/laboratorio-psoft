package com.ufcg.psoft.mercadofacil.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ufcg.psoft.mercadofacil.exception.BatchNotFoundException;
import com.ufcg.psoft.mercadofacil.exception.PaymentMethodNotAcceptedException;
import com.ufcg.psoft.mercadofacil.exception.ProductNotFoundException;
import com.ufcg.psoft.mercadofacil.exception.PurchaseCannotBeAcceptedException;
import com.ufcg.psoft.mercadofacil.exception.PurchaseNotFoundException;
import com.ufcg.psoft.mercadofacil.exception.UserNotFoundException;
import com.ufcg.psoft.mercadofacil.model.Compra;
import com.ufcg.psoft.mercadofacil.service.CompraService;


@RestController
@RequestMapping("/api")
@CrossOrigin
public class CompraController {
	
	@Autowired
	private CompraService compraService;

	@RequestMapping(value = "/carrinho/{cpfUsuario}/add/{idProduto}", method = RequestMethod.PUT)
	public ResponseEntity<?> adicionarProduto(@PathVariable("cpfUsuario") String cpfUsuario, @PathVariable("idProduto") String idProduto) {
		try {
			this.compraService.adicionaProduto(cpfUsuario, idProduto);
		} catch (UserNotFoundException e) {
			return new ResponseEntity<String>("Usuário não encontrado", HttpStatus.NOT_FOUND);
		} catch (ProductNotFoundException e) {
			return new ResponseEntity<String>("Produto não encontrado", HttpStatus.NOT_FOUND);
		} catch (BatchNotFoundException e) {
			return new ResponseEntity<String>("Não há lote do produto no sistema", HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<String>("Produto adicionado ao carrinho com sucesso", HttpStatus.OK);
	}
	
	@RequestMapping(value = "/carrinho/{cpfUsuario}/remove/{idProduto}", method = RequestMethod.PUT)
	public ResponseEntity<?> removerProduto(@PathVariable("cpfUsuario") String cpfUsuario, @PathVariable("idProduto") String idProduto) {
		try {
			this.compraService.removeProduto(cpfUsuario, idProduto);
		} catch (UserNotFoundException e) {
			return new ResponseEntity<String>("Usuário não encontrado", HttpStatus.NOT_FOUND);
		} catch (ProductNotFoundException e) {
			return new ResponseEntity<String>("Produto não encontrado", HttpStatus.NOT_FOUND);
		} catch (BatchNotFoundException e) {
			return new ResponseEntity<String>("Não há lote do produto no sistema", HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<String>("Produto removido do carrinho com sucesso", HttpStatus.OK);
	}
	
	@RequestMapping(value = "/carrinho/{cpfUsuario}/checkout", method = RequestMethod.POST)
	public ResponseEntity<?> finalizarCompra(@PathVariable("cpfUsuario") String cpfUsuario, @RequestParam(value = "formaPagamento", required = false) String formaPagamento) {
		String descritivoCompra;
		
		try {
			descritivoCompra = this.compraService.finalizaCompra(cpfUsuario, formaPagamento);
		} catch (UserNotFoundException e) {
			return new ResponseEntity<String>("Usuário não encontrado", HttpStatus.NOT_FOUND);
		} catch (PurchaseCannotBeAcceptedException e) {
			return new ResponseEntity<String>("A compra não pode ser realizada", HttpStatus.NOT_ACCEPTABLE);
		} catch (PaymentMethodNotAcceptedException e) {
			return new ResponseEntity<String>("A forma de pagamento não está disponível no sistema", HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<String>(descritivoCompra, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/carrinho/{cpfUsuario}/discard", method = RequestMethod.DELETE)
	public ResponseEntity<?> descartarCompra(@PathVariable("cpfUsuario") String cpfUsuario) {
		try {
			this.compraService.descartaCompra(cpfUsuario);
		} catch (UserNotFoundException e) {
			return new ResponseEntity<String>("Usuário não encontrado", HttpStatus.NOT_FOUND);
		} catch (BatchNotFoundException e) {
			return new ResponseEntity<String>("Não há lote do produto no sistema", HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<String>("Compra descartada com sucesso", HttpStatus.OK);
	}
	
	@RequestMapping(value = "/carrinho/{cpfUsuario}", method = RequestMethod.GET)
	public ResponseEntity<?> listarCompras(@PathVariable("cpfUsuario") String cpfUsuario) {
		List<Compra> comprasUsuario;
		
		try {
			comprasUsuario = this.compraService.listaCompras(cpfUsuario);
		} catch (UserNotFoundException e) {
			return new ResponseEntity<String>("Usuário não encontrado", HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<List<Compra>>(comprasUsuario, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/carrinho/{cpfUsuario}/{idCompra}", method = RequestMethod.GET)
	public ResponseEntity<?> exibirCompra(@PathVariable("cpfUsuario") String cpfUsuario, @PathVariable("idCompra") String idCompra) {
		Compra compra;
		
		try {
			compra = this.compraService.exibeDetalhesCompra(cpfUsuario, idCompra);
		} catch (UserNotFoundException e) {
			return new ResponseEntity<String>("Usuário não encontrado", HttpStatus.NOT_FOUND);
		} catch (PurchaseNotFoundException e) {
			return new ResponseEntity<String>("Compra não encontrada", HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Compra>(compra, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/carrinho/formas-pagamento", method = RequestMethod.GET)
	public ResponseEntity<?> listarFormasPagamento() {
		String formasPagamento = this.compraService.listaFormasPagamento();
		
		return new ResponseEntity<String>(formasPagamento, HttpStatus.OK);
	}

}