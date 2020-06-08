package com.paulo.altran.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.paulo.altran.auth.model.Usuario;
import com.paulo.altran.auth.repository.UsuarioRepository;


@Service
public class CustomDetailsService implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("Usuário e/ou senha inválidos!"));
		UserDetails user = new User(usuario.getEmail(), usuario.getSenha(), usuario.getAuthorities());
		return user;
	}
}