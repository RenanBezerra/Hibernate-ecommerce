package com.estudohibernateworkstest.detalhesimportantes;

import org.junit.Test;

import com.estudo.hibernate.works.model.Cliente;
import com.estudohibernateworkstest.EntityManagerTest;

public class ValidacaoTest extends EntityManagerTest{

	@Test
	public void validarCliente() {
		entityManager.getTransaction().begin();
		
		Cliente cliente = new Cliente();
		entityManager.merge(cliente);
		
		
		entityManager.getTransaction().commit();
	}
	
}
