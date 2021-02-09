package com.estudoHibernateWorks.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class ItemPedido {

	@EqualsAndHashCode.Include
	@Id
	private Integer id;

	private Integer pedidoId;

	private Integer produtoId;

	private BigDecimal precoProduto;
	
	private Integer quantidade;
}
