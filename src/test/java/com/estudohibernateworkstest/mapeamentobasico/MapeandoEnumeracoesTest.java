package com.estudohibernateworkstest.mapeamentobasico;

import org.junit.Assert;
import org.junit.Test;

import com.estudohibernateworks.model.Cliente;
import com.estudohibernateworks.model.SexoCliente;
import com.estudohibernateworkstest.EntityManagerTest;

public class MapeandoEnumeracoesTest extends EntityManagerTest{
	
	@Test
	public void testarEnum() {
		Cliente cliente = new Cliente();
		//cliente.setId(4);
		cliente.setNome("Jose Mineiro");
		cliente.setSexo(SexoCliente.MASCULINO);
		cliente.setCpf("777");
		
		entityManager.getTransaction().begin();
		entityManager.persist(cliente);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Cliente clienteVerificacao = entityManager.find(Cliente.class, cliente.getId());
		Assert.assertNotNull(clienteVerificacao);
	}

}
