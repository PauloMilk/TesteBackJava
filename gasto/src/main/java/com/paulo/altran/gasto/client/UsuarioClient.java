package com.paulo.altran.gasto.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.paulo.altran.gasto.dto.UsuarioDTO;

@FeignClient(decode404 = false,name = "usuarios")
public interface UsuarioClient {
	
	@GetMapping("")
	public Long getIdUser(@RequestParam String email);

	@GetMapping("/{id}")
	public UsuarioDTO findExistsById(@PathVariable Long id);
}
