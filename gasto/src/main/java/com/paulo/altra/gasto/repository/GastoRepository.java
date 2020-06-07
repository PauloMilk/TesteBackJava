package com.paulo.altra.gasto.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paulo.altra.gasto.model.Gasto;

public interface GastoRepository extends JpaRepository<Gasto, Long> {

	Optional<Gasto> findFirst1BycodigoUsuarioAndDescricaoIgnoreCaseAndCodigoCategoriaNotNull(Long codigoUsuario,
			String descricao);

	Optional<Gasto> findByIdAndCodigoUsuario(Long id, Long usuarioId);

}
