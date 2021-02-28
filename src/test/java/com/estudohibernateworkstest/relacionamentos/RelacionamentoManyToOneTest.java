package com.estudohibernateworkstest.relacionamentos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.Test;

import com.estudohibernateworks.model.Cliente;
import com.estudohibernateworks.model.ItemPedido;
import com.estudohibernateworks.model.ItemPedidoId;
import com.estudohibernateworks.model.Pedido;
import com.estudohibernateworks.model.Produto;
import com.estudohibernateworks.model.StatusPedido;
import com.estudohibernateworkstest.EntityManagerTest;

public class RelacionamentoManyToOneTest extends EntityManagerTest {

	@Test
	public void verificarRelacionamento() {
		Cliente cliente = entityManager.find(Cliente.class, 1);

		Pedido pedido = new Pedido();
		pedido.setStatus(StatusPedido.AGUARDANDO);
		pedido.setDataCriacao(LocalDateTime.now());
		pedido.setTotal(BigDecimal.TEN);

		pedido.setCliente(cliente);

		entityManager.getTransaction().begin();
		entityManager.persist(pedido);
		entityManager.getTransaction().commit();

		entityManager.clear();
		Pedido pedidoVerificacaoPedido = entityManager.find(Pedido.class, pedido.getId());
		Assert.assertNotNull(pedidoVerificacaoPedido.getCliente());
	}

	@Test
	public void verificarRelacionamentoItemPedido() {
		entityManager.getTransaction().begin();

		Cliente cliente = entityManager.find(Cliente.class, 1);
		Produto produto = entityManager.find(Produto.class, 1);

		Pedido pedido = new Pedido();
		pedido.setStatus(StatusPedido.AGUARDANDO);
		pedido.setDataCriacao(LocalDateTime.now());
		pedido.setTotal(BigDecimal.TEN);
		pedido.setCliente(cliente);

		entityManager.persist(pedido);

		ItemPedido itemPedido = new ItemPedido();
		itemPedido.setPedidoId(pedido.getId());
		itemPedido.setProdutoId(produto.getId());
		itemPedido.setPrecoProduto(produto.getPreco());
		itemPedido.setQuantidade(1);
		itemPedido.setPedido(pedido);
		itemPedido.setProduto(produto);

		entityManager.persist(itemPedido);
		entityManager.getTransaction().commit();

		entityManager.clear();

		ItemPedido itemPedidoVerificacao = entityManager.find(ItemPedido.class, new ItemPedidoId(pedido.getId(),produto.getId()));
		Assert.assertNotNull(itemPedidoVerificacao.getPedido());
		Assert.assertNotNull(itemPedidoVerificacao.getProduto());
	}

	@Test
	public void verificarRelacionamentoPedido() {
		Cliente cliente = entityManager.find(Cliente.class, 1);
		Produto produto = entityManager.find(Produto.class, 1);

		Pedido pedido = new Pedido();
		pedido.setStatus(StatusPedido.AGUARDANDO);
		pedido.setDataCriacao(LocalDateTime.now());
		pedido.setTotal(BigDecimal.TEN);
		pedido.setCliente(cliente);

		entityManager.getTransaction().begin();
		entityManager.persist(pedido);

		ItemPedido itemPedido = new ItemPedido();
		itemPedido.setPedidoId(pedido.getId());
		itemPedido.setProdutoId(produto.getId());
		itemPedido.setPrecoProduto(produto.getPreco());
		itemPedido.setQuantidade(1);
		itemPedido.setPedido(pedido);
		itemPedido.setProduto(produto);

		entityManager.persist(itemPedido);
		entityManager.getTransaction().commit();

		entityManager.clear();

		Pedido pedidoVerificacao = entityManager.find(Pedido.class, pedido.getId());
		Assert.assertFalse(pedidoVerificacao.getItens().isEmpty());

	}
}
