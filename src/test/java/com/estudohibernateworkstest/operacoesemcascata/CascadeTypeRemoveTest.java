package com.estudohibernateworkstest.operacoesemcascata;

import org.junit.Assert;

import com.estudo.hibernate.works.model.ItemPedido;
import com.estudo.hibernate.works.model.ItemPedidoId;
import com.estudo.hibernate.works.model.Pedido;
import com.estudohibernateworkstest.EntityManagerTest;

public class CascadeTypeRemoveTest extends EntityManagerTest {

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
