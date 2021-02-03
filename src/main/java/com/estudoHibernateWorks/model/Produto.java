package com.estudoHibernateWorks.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Produto {
	
	@Id
	private Integer id;
	
	private String nome;
	
	private String descricao;
	
	private BigDecimal preco;

}
