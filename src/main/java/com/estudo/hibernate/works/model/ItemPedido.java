package com.estudo.hibernate.works.model;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "item_pedido")
public class ItemPedido {

	@EmbeddedId
	private ItemPedidoId id;

	@MapsId("pedidoId")
	@ManyToOne(optional = false, cascade = CascadeType.MERGE)
	@JoinColumn(name = "pedido_id", nullable = false,
		foreignKey = @ForeignKey(name = "fk_item_pedido_pedido"))
	private Pedido pedido;

	@MapsId("produtoId")
	@ManyToOne(optional = false)
	@JoinColumn(name = "produto_id", nullable = false, foreignKey = @ForeignKey(name = "fk_item_pedido_produto"))
	private Produto produto;

	@Column(name = "preco_produto",nullable = false)
	private BigDecimal precoProduto;

	@Column(nullable = false)
	private Integer quantidade;
}
