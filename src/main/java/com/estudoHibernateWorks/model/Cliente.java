package com.estudohibernateworks.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "cliente")
public class Cliente {

	@EqualsAndHashCode.Include
	@Id
	private Integer id;

	private String nome;
	
	private SexoCliente sexo;

}
