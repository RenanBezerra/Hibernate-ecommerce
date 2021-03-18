package com.estudohibernateworkstest.relacionamentos;

import org.junit.Test;

import com.estudo.hibernate.works.model.Pedido;
import com.estudohibernateworkstest.EntityManagerTest;

public class EagerELazyTest extends EntityManagerTest {

	@Test
	public void verificarComportamento() {
		Pedido pedido = entityManager.find(Pedido.class, 1);

		pedido.getItens();
	}
}
