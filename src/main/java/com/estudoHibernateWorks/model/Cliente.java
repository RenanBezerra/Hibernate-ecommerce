package com.estudoHibernateWorks.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Cliente {

	@Id
	private Integer id;

	private String nome;

}
