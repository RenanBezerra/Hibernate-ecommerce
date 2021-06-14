package com.estudo.hibernate.works.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@DiscriminatorColumn(name = "tipo_pagamento", discriminatorType = DiscriminatorType.STRING)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Entity
@Table(name = "pagamento")
public abstract class Pagamento  {

	@Id
	private Integer id;
	
	@Version
	private Integer versao;
	
	@NotNull
	@MapsId
	@OneToOne(optional = false)
	@JoinColumn(name = "pedido_id", nullable = false, foreignKey = @ForeignKey(name = "fk_pagamento_pedido"))
	private Pedido pedido;

	@NotNull
	@Column(length = 30, nullable = false)
	@Enumerated(EnumType.STRING)
	private StatusPagamento status;
}
