package com.estudohibernateworkstest.conehcendoentitymanager;

import org.junit.Test;

import com.estudohibernateworks.model.Pedido;
import com.estudohibernateworks.model.StatusPedido;
import com.estudohibernateworkstest.EntityManagerTest;

public class GerenciamentoTransacoesTest extends EntityManagerTest {

	@Test(expected = Exception.class)
	public void abrirFecharCancelarTransacao() {

		try {
			entityManager.getTransaction().commit();
			metodoDeNegocio();
			entityManager.getTransaction().rollback();

		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			throw e;
		}

	}

	public void metodoDeNegocio() {
		Pedido pedido = entityManager.find(Pedido.class, 1);

		entityManager.getTransaction().begin();
		pedido.setStatus(StatusPedido.PAGO);

		if (pedido.getPagamento() == null) {
			throw new RuntimeException("Pedido ainda não foi pago");
		}

	}

}