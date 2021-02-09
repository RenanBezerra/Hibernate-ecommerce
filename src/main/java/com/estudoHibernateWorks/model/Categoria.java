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
@Table(name = "categoria")
public class Categoria {

	@EqualsAndHashCode.Include
	@Id
	private Integer id;

	private String nome;

	@Column(name = "categoria_pai_id")
	private Integer categoriaPaiId;

}
