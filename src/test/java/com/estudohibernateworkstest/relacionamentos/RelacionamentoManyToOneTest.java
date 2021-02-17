package com.estudohibernateworkstest.relacionamentos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.propertyeditors.ClassEditor;

import com.estudohibernateworks.model.Cliente;
import com.estudohibernateworks.model.Pedido;
import com.estudohibernateworks.model.StatusPedido;
import com.estudohibernateworkstest.EntityManagerTest;

public class RelacionamentoManyToOneTest extends EntityManagerTest{

	@Test
	public void verificarRelacionamento() {
		Cliente cliente = entityManager.find(Cliente.class, 1);
		
		Pedido pedido = new Pedido();
		pedido.setStatus(StatusPedido.AGUARDANDO);
		pedido.setDataPedido(LocalDateTime.now());
		pedido.setTotal(BigDecimal.TEN);
		
		pedido.setCliente(cliente);
		
		entityManager.getTransaction().begin();
		entityManager.persist(pedido);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		Pedido pedidoVerificacaoPedido = entityManager.find(Pedido.class, pedido.getId());
		Assert.assertNotNull(pedidoVerificacaoPedido.getCliente());
	}
}
