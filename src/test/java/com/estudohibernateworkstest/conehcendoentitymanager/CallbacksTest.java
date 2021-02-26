package com.estudohibernateworkstest.conehcendoentitymanager;

import org.junit.Assert;
import org.junit.Test;

import com.estudohibernateworks.model.Cliente;
import com.estudohibernateworks.model.Pedido;
import com.estudohibernateworks.model.StatusPedido;
import com.estudohibernateworkstest.EntityManagerTest;

public class CallbacksTest extends EntityManagerTest {

	@Test
	public void acionarCallbacks() {
		Cliente cliente = entityManager.find(Cliente.class, 1);

		Pedido pedido = new Pedido();

		pedido.setCliente(cliente);
		pedido.setStatus(StatusPedido.AGUARDANDO);

		entityManager.getTransaction().begin();

		entityManager.persist(pedido);
		entityManager.flush();

		pedido.setStatus(StatusPedido.PAGO);

		entityManager.getTransaction().commit();

		entityManager.clear();

		Pedido pedidoVerificacao = entityManager.find(Pedido.class, pedido.getId());
		Assert.assertNotNull(pedidoVerificacao.getDataCriacao());
		Assert.assertNotNull(pedidoVerificacao.getDataUltimaAtualizacao());
	}

}