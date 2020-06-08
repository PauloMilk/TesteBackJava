package com.paulo.altran.usuario.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paulo.altran.usuario.exception.ResourceNotFoundException;
import com.paulo.altran.usuario.model.Usuario;
import com.paulo.altran.usuario.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	
	public Long fingIdByEmail(String email) {
		Optional<Usuario> findByEmail = this.usuarioRepository.findByEmail(email);
		return findByEmail.get().getId();
		
	}

	public boolean findExistById(Long id) {		
		return usuarioRepository.existsById(id);
	}

	public Usuario buscarPeloId(Long id) {
		Usuario usuario = usuarioRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException("Usuário não encontrado pelo id: "+id)); 
		return usuario;
	}

}
