package com.ufcg.psoft.mercadofacil.repository;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ufcg.psoft.mercadofacil.dto.UsuarioDTO;
import com.ufcg.psoft.mercadofacil.model.Usuario;


@Repository
public class UsuarioRepository {

	private Map<String, Usuario> usuarios;
	
	public UsuarioRepository() {
		this.usuarios = new HashMap<String, Usuario>();
	}
	
	public String cadastraUsuario(Usuario usuario) {
		this.usuarios.put(usuario.getCpf(), usuario);
		
		return usuario.getCpf();
	}
	
	public void atualizaUsuario(Usuario usuario, UsuarioDTO usuarioDTO) {
		usuario.setEndereco(usuarioDTO.getEndereco());
		usuario.setTelefone(usuarioDTO.getTelefone());
		
		this.usuarios.replace(usuario.getCpf(), usuario);
	}
	
	public void alteraPerfilUsuario(String cpf, String perfil) {
		Usuario usuario = this.usuarios.get(cpf);
		
		usuario.alteraPerfil(perfil);
	}
	
	public void removeUsuario(String cpf) {
		this.usuarios.remove(cpf);
	}
	
	public Usuario getUsuario(String cpf) {
		return this.usuarios.get(cpf);
	}
	
	public Collection<Usuario> getAll() {
		return this.usuarios.values();
	}
	
}