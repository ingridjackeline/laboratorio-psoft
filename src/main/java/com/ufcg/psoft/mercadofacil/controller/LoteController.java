package com.ufcg.psoft.mercadofacil.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.ufcg.psoft.mercadofacil.dto.LoteDTO;
import com.ufcg.psoft.mercadofacil.exception.ProductNotFoundException;
import com.ufcg.psoft.mercadofacil.model.Lote;
import com.ufcg.psoft.mercadofacil.exception.BatchNotFoundException;
import com.ufcg.psoft.mercadofacil.service.LoteService;


@RestController
@RequestMapping("/api")
@CrossOrigin
public class LoteController {
	
	@Autowired
	private LoteService loteService;
	
	@RequestMapping(value = "/lote/", method = RequestMethod.POST)
	public ResponseEntity<?> criarLote(@RequestBody LoteDTO loteDTO, UriComponentsBuilder ucBuilder) {
		String loteID;
		
		try {
			loteID = this.loteService.addLote(loteDTO);
		} catch (ProductNotFoundException e) {
			return new ResponseEntity<String>("Produto não encontrado", HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<String>("Lote cadastrado com ID:" + loteID, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/lote/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> consultarLote(@PathVariable("id") String id) {
		Lote lote;
		
		try {
			lote = this.loteService.getLoteById(id);
		} catch (BatchNotFoundException e) {
			return new ResponseEntity<String>("Lote não encontrado", HttpStatus.NOT_FOUND);
		}
			
		return new ResponseEntity<Lote>(lote, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/lote/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> atualizarLote(@PathVariable("id") String id, @RequestBody LoteDTO loteDTO, UriComponentsBuilder ucBuilder) {
		try {
			this.loteService.editLote(id, loteDTO);
		} catch (BatchNotFoundException e) {
			return new ResponseEntity<String>("Lote não encontrado", HttpStatus.NOT_FOUND);
		} catch (ProductNotFoundException e) {
			return new ResponseEntity<String>("Produto não encontrado", HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<String>("Lote com ID " + id + " atualizado", HttpStatus.OK);
	}
	
	@RequestMapping(value = "/lote/{id}", method = RequestMethod.DELETE) 
	public ResponseEntity<?> removerLote(@PathVariable("id") String id) {
		try {
			this.loteService.delLote(id);
		} catch (BatchNotFoundException e) {
			return new ResponseEntity<String>("Lote não encontrado", HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<String>("Lote com ID " + id + " removido", HttpStatus.OK);
	}
	
	@RequestMapping(value = "/lotes", method = RequestMethod.GET)
	public ResponseEntity<?> listarLotes() {
		List<Lote> lotes = this.loteService.listarLotes();
		
		return new ResponseEntity<List<Lote>>(lotes, HttpStatus.OK);
	}
	
}