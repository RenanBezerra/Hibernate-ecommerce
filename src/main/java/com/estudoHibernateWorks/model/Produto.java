package com.estudohibernateworks.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "produto")
public class Produto {
	
	@EqualsAndHashCode.Include
	@Id
	private Integer id;
	
	private String nome;
	
	private String descricao;
	
	private BigDecimal preco;

}
