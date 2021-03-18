package com.estudohibernateworkstest.mapeamentoavancado;

import org.junit.Assert;
import org.junit.Test;

import com.estudo.hibernate.works.model.Cliente;
import com.estudohibernateworkstest.EntityManagerTest;

public class PropriedadesTransientesTest extends EntityManagerTest {

	@Test
	public void validarPrimeiroNome() {
		Cliente cliente = entityManager.find(Cliente.class, 1);

		Assert.assertEquals("Fernando", cliente.getPrimeiroNome());
	}
}
