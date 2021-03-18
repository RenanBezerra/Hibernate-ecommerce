package com.estudohibernateworkstest.conehcendoentitymanager;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.Test;

import com.estudo.hibernate.works.model.Cliente;
import com.estudo.hibernate.works.model.Pedido;
import com.estudo.hibernate.works.model.StatusPedido;
import com.estudohibernateworkstest.EntityManagerTest;

public class CallbacksTest extends EntityManagerTest {

	@Test
	public void acionarCallbacks() {
		Cliente cliente = entityManager.find(Cliente.class, 1);

		Pedido pedido = new Pedido();
		pedido.setDataCriacao(LocalDateTime.now());
		pedido.setCliente(cliente);
		pedido.setStatus(StatusPedido.AGUARDANDO);
		pedido.setTotal(BigDecimal.TEN);

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