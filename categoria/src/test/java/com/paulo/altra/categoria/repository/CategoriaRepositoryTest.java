package com.paulo.altra.categoria.repository;

import java.util.Optional;
import java.util.Random;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.paulo.altran.categoria.model.Categoria;
import com.paulo.altran.categoria.repository.CategoriaRepository;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class CategoriaRepositoryTest {

	@Autowired
	TestEntityManager entityManager;
	
	@Autowired
	CategoriaRepository repository;
	
	@Test
	@DisplayName("Deve retornar um optional preenchido quando buscar por um nome existente")
	public void testarFindByNomePreenchido() {
		Categoria categoria = this.criarCategoria();
		entityManager.persist(categoria);
		
		Optional<Categoria> categoriaEncontrada = repository.findByNome(categoria.getNome());
		
		Assertions.assertThat(categoriaEncontrada.isPresent()).isTrue();
	}
	
	@Test
	@DisplayName("Deve retornar um optional nulo quando buscar por um nome inexistente")
	public void testarFindByNomeNulo() {
		Categoria categoria = this.criarCategoria("Mercado");		
		
		Optional<Categoria> categoriaEncontrada = repository.findByNome(categoria.getNome());
		
		Assertions.assertThat(categoriaEncontrada.isPresent()).isFalse();
	}
		
	
	@Test
	@DisplayName("Deve retornar um lista paginada de categoria quando informar um nome para pesquisa")
	public void testarPaginacao() {
		Categoria categoria = this.criarCategoria("Mercado");
		Categoria categoria2 = this.criarCategoria("Mercadoss");
		Categoria categoria3 = this.criarCategoria("Mercadossss");
		entityManager.persist(categoria);
		entityManager.persist(categoria2);
		entityManager.persist(categoria3);
		
		Optional<Categoria> categoriaEncontrada = repository.findByNome(categoria.getNome());
		
		Assertions.assertThat(categoriaEncontrada.isPresent()).isFalse();
	}
	
	
	private Categoria criarCategoria(String nome) {
		return Categoria.builder().nome(nome).build();
	}
		
	
	
}
