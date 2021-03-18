package com.estudo.hibernate.works.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "estoque")
public class Estoque extends EntidadeBaseInteger {

	@OneToOne(optional = false)
	@JoinColumn(name = "produto_id")
	private Produto produto;

	private Integer quantidade;

}
