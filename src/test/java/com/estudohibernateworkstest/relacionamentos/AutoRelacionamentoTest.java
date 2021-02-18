package com.estudohibernateworkstest.relacionamentos;

import org.junit.Assert;
import org.junit.Test;

import com.estudohibernateworks.model.Pedido;
import com.estudohibernateworkstest.EntityManagerTest;

public class AutoRelacionamentoTest extends EntityManagerTest {

	@Test
	public void removerEntidadeRelacionada() {
		Pedido pedido = entityManager.find(Pedido.class, 1);

		Assert.assertFalse(pedido.getItens().isEmpty());

		entityManager.getTransaction().begin();
		pedido.getItens().forEach(i -> entityManager.remove(i));
		entityManager.remove(pedido);
		entityManager.getTransaction().commit();

		entityManager.clear();

		Pedido pedidoVerificacao = entityManager.find(Pedido.class, 1);
		Assert.assertNull(pedidoVerificacao);
	}

}
