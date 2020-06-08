package com.paulo.altran.gasto.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.paulo.altran.gasto.client.CategoriaClient;
import com.paulo.altran.gasto.client.UsuarioClient;
import com.paulo.altran.gasto.dto.CategoriaCadastroDTO;
import com.paulo.altran.gasto.dto.CategoriaDTO;
import com.paulo.altran.gasto.exception.BussinessException;
import com.paulo.altran.gasto.exception.ResourceNotFoundException;
import com.paulo.altran.gasto.model.Gasto;
import com.paulo.altran.gasto.repository.GastoRepository;

import feign.FeignException;

@Service
public class GastoService {

	@Autowired
	private GastoRepository gastoRepository;

	@Autowired
	private CategoriaClient categoriaClient;

	@Autowired
	private UsuarioClient usuarioClient;

	private Map<Long, String> categorias = new HashMap<>();

	public Gasto salvarGasto(Gasto gasto) {
//		verificaUsuario(gasto.getCodigoUsuario());
		Optional<Gasto> gastoComCategoriaIgual = gastoRepository
				.findFirst1BycodigoUsuarioAndDescricaoIgnoreCaseAndCodigoCategoriaNotNull(gasto.getCodigoUsuario(),
						gasto.getDescricao());
		if (gastoComCategoriaIgual.isPresent()) {
			gasto.setCodigoCategoria(gastoComCategoriaIgual.get().getCodigoCategoria());
			CategoriaDTO categoria = categoriaClient.bucarpeloId(gasto.getCodigoCategoria());
			gasto.setCategoria(categoria.getNome());
		}
		Gasto gastoSalvo = gastoRepository.save(gasto);
		return gastoSalvo;
	}

	public Gasto salvarCategoria(String categoria, Long id, String email) {
		Long usuarioId = usuarioClient.getIdUser(email);
		Gasto gasto = gastoRepository.findByIdAndCodigoUsuario(id, usuarioId)
				.orElseThrow(() -> new ResourceNotFoundException("Gasto não encontrado pelo id: " + id));

		if (gasto.getCodigoCategoria() != null)
			throw new BussinessException("Não é possível alterar um gasto que ja possui uma categoria!");

		CategoriaCadastroDTO dto = CategoriaCadastroDTO.builder().nome(categoria).build();
		CategoriaDTO categoriaSalva = categoriaClient.salvarCategoria(dto);
		gasto.setCodigoCategoria(categoriaSalva.getId());
		gasto = this.gastoRepository.save(gasto);
		gasto.setCategoria(categoriaSalva.getNome());
		return gasto;
	}

	public Gasto obterPorId(Long id, String email) {
		Long usuarioId = usuarioClient.getIdUser(email);
		Gasto gasto = gastoRepository.findByIdAndCodigoUsuario(id, usuarioId)
				.orElseThrow(() -> new ResourceNotFoundException("Gasto não encontrado pelo id: " + id));
		CategoriaDTO categoriaSalva = categoriaClient.bucarpeloId(gasto.getCodigoCategoria());
		gasto.setCategoria(categoriaSalva.getNome());
		return gasto;
	}

	public Page<Gasto> obterGastosPorCodigoUsuario(Gasto filter, String email, Pageable pageable) {
//		Long usuarioId = usuarioClient.getIdUser(email);
		filter.setCodigoUsuario(1l);

		Example<Gasto> example = Example.of(filter,
				ExampleMatcher.matching().withIgnoreCase().withIgnoreNullValues()
						.withStringMatcher(StringMatcher.CONTAINING)						
						.withMatcher("cd_usuario", GenericPropertyMatchers.exact()));
		Page<Gasto> result = gastoRepository.findAll(example, pageable);
		return result;
	}

	public Page<Gasto> obterGastosPorData(LocalDate data, String email, Pageable pageable) {
//		Long usuarioId = usuarioClient.getIdUser(email);
//		filter.setCodigoUsuario(1l);
		Page<Gasto> result = gastoRepository.buscarData(data, 1l, pageable);
		return result;
	}
	
	private void verificaUsuario(Long id) {
		try {
			this.usuarioClient.findExistsById(id);
		} catch (FeignException e) {
			if (e.status() == 404) {
				throw new ResourceNotFoundException("Usuario não encontrado pelo id: " + id);
			} else {
				System.out.println("teste");
				throw new ResponseStatusException(HttpStatus.valueOf(e.status()), "Lançou outra");
			}

		}
	}

}
