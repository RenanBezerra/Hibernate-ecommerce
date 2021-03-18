package com.estudo.hibernate.works.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "nota_fiscal")
public class NotaFiscal extends EntidadeBaseInteger {

	// @JoinTable(name = "pedido_nota_fiscal", joinColumns = @JoinColumn(name =
	// "nota_fiscal_id", unique = true),
	// inverseJoinColumns = @JoinColumn(name = "pedido_id", unique = true))
	@MapsId
	@OneToOne(optional = false)
	@JoinColumn(name = "pedido_id")
	private Pedido pedido;

	@Column(nullable = false)
	@Lob
	private byte[] xml;

	@Column(name = "data_emissao", nullable = false)
	private Date dataEmissao;

}
