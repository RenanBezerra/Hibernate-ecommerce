package com.estudohibernateworkstest.mapeamentoavancado;

import org.junit.Assert;
import org.junit.Test;

import com.estudohibernateworks.model.Cliente;
import com.estudohibernateworkstest.EntityManagerTest;

public class HerancaTest extends EntityManagerTest{

	@Test
	public void salvarCliente() {
		Cliente cliente = new Cliente();
		cliente.setNome("Fernanda Morais");
		
		entityManager.getTransaction().begin();
		entityManager.persist(cliente);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Cliente clienteVerificacao = entityManager.find(Cliente.class, cliente.getId());
		Assert.assertNotNull(clienteVerificacao.getId());
		
	}
}
