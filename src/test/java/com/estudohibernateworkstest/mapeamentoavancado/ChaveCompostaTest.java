package com.estudohibernateworkstest.mapeamentoavancado;

import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.Test;

import com.estudo.hibernate.works.model.Cliente;
import com.estudo.hibernate.works.model.ItemPedido;
import com.estudo.hibernate.works.model.ItemPedidoId;
import com.estudo.hibernate.works.model.Pedido;
import com.estudo.hibernate.works.model.Produto;
import com.estudo.hibernate.works.model.StatusPedido;
import com.estudohibernateworkstest.EntityManagerTest;

public class ChaveCompostaTest extends EntityManagerTest {

	@Test
	public void salvarItem() {
		entityManager.getTransaction().begin();

		Cliente cliente = entityManager.find(Cliente.class, 1);
		Produto produto = entityManager.find(Produto.class, 1);

		Pedido pedido = new Pedido();
		pedido.setDataCriacao(LocalDateTime.now());
		pedido.setCliente(cliente);
		pedido.setDataCriacao(LocalDateTime.now());
		pedido.setStatus(StatusPedido.AGUARDANDO);
		pedido.setTotal(produto.getPreco());

		ItemPedido itemPedido = new ItemPedido();
//		itemPedido.setPedidoId(pedido.getId()); //@IdClass
//		itemPedido.setProdutoId(produto.getId());
		// itemPedido.setId(new ItemPedidoId(pedido.getId(), produto.getId()));
		itemPedido.setId(new ItemPedidoId());
		itemPedido.setPedido(pedido);
		itemPedido.setProduto(produto);
		itemPedido.setPrecoProduto(produto.getPreco());
		itemPedido.setQuantidade(1);

		entityManager.persist(pedido);
		entityManager.persist(itemPedido);

		entityManager.getTransaction().commit();

		entityManager.clear();

		Pedido pedidoVerificacao = entityManager.find(Pedido.class, pedido.getId());
		Assert.assertNotNull(pedidoVerificacao);
		Assert.assertFalse(pedidoVerificacao.getItens().isEmpty());
	}

	@Test
	public void buscarItem() {

		ItemPedido itemPedido = entityManager.find(ItemPedido.class, new ItemPedidoId(1, 1));

		Assert.assertNotNull(itemPedido);

	}

}
