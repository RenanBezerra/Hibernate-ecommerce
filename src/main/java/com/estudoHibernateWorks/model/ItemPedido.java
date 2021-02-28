package com.estudohibernateworks.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "item_pedido")
public class ItemPedido {

	@EmbeddedId
	private ItemPedidoId id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "pedido_id", insertable = false, updatable = false)
	private Pedido pedido;

	@ManyToOne(optional = false)
	@JoinColumn(name = "produto_id", insertable = false, updatable = false)
	private Produto produto;

	@Column(name = "preco_produto")
	private BigDecimal precoProduto;

	private Integer quantidade;
}
