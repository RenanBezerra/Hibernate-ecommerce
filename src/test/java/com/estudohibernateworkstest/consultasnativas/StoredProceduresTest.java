package com.estudohibernateworkstest.consultasnativas;

import java.util.List;

import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

import org.junit.Assert;
import org.junit.Test;

import com.estudo.hibernate.works.model.Cliente;
import com.estudohibernateworkstest.EntityManagerTest;

public class StoredProceduresTest extends EntityManagerTest {

	@Test
	public void receberListaDaProcedure() {
		StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("compraram_acima_media",
				Cliente.class);

		storedProcedureQuery.registerStoredProcedureParameter("ano", Integer.class, ParameterMode.IN);

		storedProcedureQuery.setParameter("ano", 2020);

		List<Cliente> lista = storedProcedureQuery.getResultList();

		Assert.assertFalse(lista.isEmpty());
	}

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
