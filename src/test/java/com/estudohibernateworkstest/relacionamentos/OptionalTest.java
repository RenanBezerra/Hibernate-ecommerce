package com.estudohibernateworkstest.relacionamentos;

import org.junit.Test;

import com.estudohibernateworks.model.Pedido;
import com.estudohibernateworkstest.EntityManagerTest;

public class OptionalTest extends EntityManagerTest {

	@Test
	public void verificarComportamento() {
		Pedido pedido = entityManager.find(Pedido.class, 1);

		pedido.getItens();
	}
}
