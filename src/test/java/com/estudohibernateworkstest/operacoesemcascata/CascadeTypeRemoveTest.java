package com.estudohibernateworkstest.operacoesemcascata;

import org.junit.Assert;
import org.junit.Test;

import com.estudo.hibernate.works.model.ItemPedido;
import com.estudo.hibernate.works.model.ItemPedidoId;
import com.estudo.hibernate.works.model.Pedido;
import com.estudo.hibernate.works.model.Produto;
import com.estudohibernateworkstest.EntityManagerTest;

public class CascadeTypeRemoveTest extends EntityManagerTest {

	//@Test
	public void removerItensOrfaos() {
		Pedido pedido = entityManager.find(Pedido.class, 1);

		Assert.assertFalse(pedido.getItens().isEmpty());

		entityManager.getTransaction().begin();
		pedido.getItens().clear();// ,cascade = CascadeType.PERSIST, orphanRemoval = true)
		entityManager.getTransaction().commit();

		entityManager.clear();

		Pedido pedidoVerificacao = entityManager.find(Pedido.class, pedido.getId());
		Assert.assertTrue(pedidoVerificacao.getItens().isEmpty());

	}

	@Test
	public void removerRelacaoProdutoCategoria() {
		Produto produto = entityManager.find(Produto.class, 1);

		Assert.assertFalse(produto.getCategorias().isEmpty());

		entityManager.getTransaction().begin();
		produto.getCategorias().clear();
		entityManager.getTransaction().commit();

		entityManager.clear();

		Produto produtoVerificacao = entityManager.find(Produto.class, produto.getId());
		Assert.assertTrue(produtoVerificacao.getCategorias().isEmpty());
	}

	// @Test
	public void removerPedidoEItens() {
		Pedido pedido = entityManager.find(Pedido.class, 1);

		entityManager.getTransaction().begin();
		entityManager.remove(pedido); // Necessario CascadeType.REMOVE no atributo "itens"
		entityManager.getTransaction().commit();

		entityManager.clear();

		Pedido pedidoVerificacao = entityManager.find(Pedido.class, pedido.getId());
		Assert.assertNull(pedidoVerificacao);
	}

	// @Test
	public void removerItemPedidoEPedido() {
		ItemPedido itemPedido = entityManager.find(ItemPedido.class, new ItemPedidoId(1, 1));

		entityManager.getTransaction().begin();
		entityManager.remove(itemPedido); // necessario CascadeType.REMOVE no atributo "pedido"
		entityManager.getTransaction().commit();

		entityManager.clear();

		Pedido PedidoVerificacao = entityManager.find(Pedido.class, itemPedido.getPedido().getId());
		Assert.assertNull(PedidoVerificacao);
	}
}
