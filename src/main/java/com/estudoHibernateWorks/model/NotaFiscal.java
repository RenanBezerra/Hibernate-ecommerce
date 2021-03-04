package com.estudohibernateworks.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "nota_fiscal")
public class NotaFiscal {

	@EqualsAndHashCode.Include
	@Id
	@Column(name = "pedido_id")
	private Integer id;

	// @JoinTable(name = "pedido_nota_fiscal", joinColumns = @JoinColumn(name =
	// "nota_fiscal_id", unique = true),
	// inverseJoinColumns = @JoinColumn(name = "pedido_id", unique = true))
	@MapsId
	@OneToOne(optional = false)
	@JoinColumn(name = "pedido_id")
	private Pedido pedido;

	private String xml;

	@Column(name = "data_emissao")
	private Date dataEmissao;

}
