package com.estudoHibernateWorks.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Estoque {

	@EqualsAndHashCode.Include
	@Id
	private Integer id;

	private Integer produtoId;

	private Integer quantidade;

}
