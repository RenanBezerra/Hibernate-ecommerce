package com.estudohibernateworkstest.consultasnativas;

import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

import org.junit.Assert;
import org.junit.Test;

import com.estudohibernateworkstest.EntityManagerTest;

public class StoredProceduresTest extends EntityManagerTest {

	@Test
	public void usarParametrosIneOut() {
		StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("buscar_nome_produto");
		storedProcedureQuery.registerStoredProcedureParameter("produto_id", Integer.class, ParameterMode.IN);

		storedProcedureQuery.registerStoredProcedureParameter("produto_nome", String.class, ParameterMode.OUT);
		storedProcedureQuery.setParameter("produto_id", 1);

		String nome = (String) storedProcedureQuery.getOutputParameterValue("produto_nome");
		Assert.assertEquals("Kindle", nome);
	}
}
