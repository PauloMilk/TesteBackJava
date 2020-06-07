package com.paulo.altra.gasto.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_gastos")
public class Gasto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cd_gasto")
	private Long id;

	@Column(name = "ds_gasto")
	private String descricao;

	@Column(name = "vl_gasto")
	private Double valor;

	@Column(name = "cd_usuario")
	private Long codigoUsuario;

	@Column(name = "dt_gasto")
	@Temporal(TemporalType.TIMESTAMP)
	private Date data;

	@Column(name = "cd_categoria")
	private Long codigoCategoria;

	@Column(name = "nm_categoria")
	private String categoria;

}
