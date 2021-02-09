package com.estudoHibernateWorks.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class NotaFiscal {

	@EqualsAndHashCode.Include
	@Id
	private Integer id;

	private Integer pedidoId;
	
	private String xml;
	
	private Date dataEmissao;

}
