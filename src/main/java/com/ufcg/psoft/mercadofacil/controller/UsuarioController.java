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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.ufcg.psoft.mercadofacil.dto.UsuarioDTO;
import com.ufcg.psoft.mercadofacil.exception.CpfUserFoundException;
import com.ufcg.psoft.mercadofacil.exception.ProfileNotFoundException;
import com.ufcg.psoft.mercadofacil.exception.UserNotFoundException;
import com.ufcg.psoft.mercadofacil.model.Usuario;
import com.ufcg.psoft.mercadofacil.service.UsuarioService;


@RestController
@RequestMapping("/api")
@CrossOrigin
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	
	@RequestMapping(value = "/usuario/", method = RequestMethod.POST)
	public ResponseEntity<?> cadastrarUsuario(@RequestBody UsuarioDTO usuarioDTO, UriComponentsBuilder ucBuilder, @RequestParam(value = "perfil", required = false) String perfil) {
		String usuarioCPF;
		
		try {
			usuarioCPF = this.usuarioService.cadastraUsuario(usuarioDTO, perfil);
		} catch (CpfUserFoundException e) {
			return new ResponseEntity<String>("CPF já cadastrado no sistema", HttpStatus.CONFLICT);
		} catch (ProfileNotFoundException e) {
			return new ResponseEntity<String>("O perfil de usuário não está disponível no sistema", HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<String>("Usuário com CPF " + usuarioCPF + " cadastrado com sucesso", HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/usuario/{cpf}", method = RequestMethod.PUT)
	public ResponseEntity<?> atualizarUsuario(@PathVariable("cpf") String cpf, @RequestBody UsuarioDTO usuarioDTO, UriComponentsBuilder ucBuilder) {
		try {
			this.usuarioService.atualizaUsuario(cpf, usuarioDTO);
		} catch (UserNotFoundException e) {
			return new ResponseEntity<String>("Usuário não encontrado", HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<String>("Usuário com CPF " + cpf + " atualizado", HttpStatus.OK);
	}
	
	@RequestMapping(value = "/usuario/{cpf}/perfil/{perfil}", method = RequestMethod.PUT)
	public ResponseEntity<?> alterarPerfilUsuario(@PathVariable("cpf") String cpf, @RequestParam(value = "perfil", required = false) String perfil) {
		try {
			this.usuarioService.alteraPerfilUsuario(cpf, perfil);
		} catch (UserNotFoundException e) {
			return new ResponseEntity<String>("Usuário não encontrado", HttpStatus.NOT_FOUND);
		} catch (ProfileNotFoundException e) {
			return new ResponseEntity<String>("O perfil de usuário não está disponível no sistema", HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<String>("Perfil do usuário com CPF " + cpf + " alterado", HttpStatus.OK);
	}
	
	@RequestMapping(value = "/usuario/{cpf}", method = RequestMethod.DELETE)
	public ResponseEntity<?> removerUsuario(@PathVariable("cpf") String cpf) {
		try {
			this.usuarioService.removeUsuario(cpf);
		} catch (UserNotFoundException e) {
			return new ResponseEntity<String>("Usuário não encontrado", HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<String>("Usuário com CPF " + cpf + " removido", HttpStatus.OK);
	}
	
	@RequestMapping(value = "/usuario/{cpf}", method = RequestMethod.GET)
	public ResponseEntity<?> acessarUsuario(@PathVariable("cpf") String cpf) {
		Usuario usuario;
		
		try {
			usuario = this.usuarioService.getUsuarioByCpf(cpf);
		} catch (UserNotFoundException e) {
			return new ResponseEntity<String>("Usuário não encontrado", HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/usuarios", method = RequestMethod.GET)
	public ResponseEntity<?> listarUsuarios() {
		List<String> usuarios = this.usuarioService.listaUsuarios();
		
		return new ResponseEntity<List<String>>(usuarios, HttpStatus.OK);
	}
	
}