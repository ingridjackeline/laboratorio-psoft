package com.ufcg.psoft.mercadofacil.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.mercadofacil.dto.UsuarioDTO;
import com.ufcg.psoft.mercadofacil.exception.CpfUserFoundException;
import com.ufcg.psoft.mercadofacil.exception.ProfileNotFoundException;
import com.ufcg.psoft.mercadofacil.exception.UserNotFoundException;
import com.ufcg.psoft.mercadofacil.model.Usuario;
import com.ufcg.psoft.mercadofacil.repository.UsuarioRepository;


@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public String cadastraUsuario(UsuarioDTO usuarioDTO, String perfil) throws CpfUserFoundException, ProfileNotFoundException {
		Usuario usuario = new Usuario(usuarioDTO.getCpf(), usuarioDTO.getNome(), usuarioDTO.getEndereco(), usuarioDTO.getTelefone());
		
		this.verificaCpfUsuario(usuario.getCpf());
		
		if (!usuario.alteraPerfil(perfil)) {
			throw new ProfileNotFoundException("Perfil de usuário não disponível no sistema");
		}
		
		this.usuarioRepository.cadastraUsuario(usuario);
		
		return usuario.getCpf();
	}
	
	public void atualizaUsuario(String cpf, UsuarioDTO usuarioDTO) throws UserNotFoundException {
		Usuario usuario = this.usuarioRepository.getUsuario(cpf);
		
		this.verificaUsuario(usuario, cpf);
		
		this.usuarioRepository.atualizaUsuario(usuario, usuarioDTO);
	}
	
	public void alteraPerfilUsuario(String cpf, String perfil) throws UserNotFoundException, ProfileNotFoundException {
		Usuario usuario = this.usuarioRepository.getUsuario(cpf);
		
		this.verificaUsuario(usuario, cpf);
		
		if (!usuario.alteraPerfil(perfil)) {
			throw new ProfileNotFoundException("Perfil de usuário não disponível no sistema");
		}
		
		this.usuarioRepository.alteraPerfilUsuario(cpf, perfil);
	}
	
	public void removeUsuario(String cpf) throws UserNotFoundException {
		Usuario usuario = this.usuarioRepository.getUsuario(cpf);
		
		this.verificaUsuario(usuario, cpf);
		
		this.usuarioRepository.removeUsuario(cpf);
	}
	
	public Usuario getUsuarioByCpf(String cpf) throws UserNotFoundException {
		Usuario usuario = this.usuarioRepository.getUsuario(cpf);
		
		this.verificaUsuario(usuario, cpf);
				
		return usuario;
	}
	
	public List<String> listaUsuarios() {
		List<String> usuarios = new ArrayList<String>();
		
		for (Usuario usuario : this.usuarioRepository.getAll()) {
			usuarios.add(usuario.toString());
		}
		
		return usuarios;
	}
	
	private void verificaUsuario(Usuario usuario, String cpf) throws UserNotFoundException {
		if (usuario == null) {
			throw new UserNotFoundException("Usuário com CPF " + cpf + " não cadastrado no sistema");
		}
	}
	
	private void verificaCpfUsuario(String cpf) throws CpfUserFoundException {
		if (this.usuarioRepository.getUsuario(cpf) != null) {
			throw new CpfUserFoundException("O CPF " + cpf + " já está cadastrado no sistema");
		}
	}
	
}