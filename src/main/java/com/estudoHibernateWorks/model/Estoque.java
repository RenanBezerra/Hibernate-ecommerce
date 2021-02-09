package com.estudohibernateworks.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "estoque")
public class Estoque {

	@EqualsAndHashCode.Include
	@Id
	private Integer id;

	@Column(name = "produto_id")
	private Integer produtoId;

	private Integer quantidade;

}
