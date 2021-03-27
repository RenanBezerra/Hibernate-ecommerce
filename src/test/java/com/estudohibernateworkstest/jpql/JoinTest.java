package com.estudohibernateworkstest.jpql;

import java.util.List;

import javax.persistence.TypedQuery;

import org.junit.Assert;
import org.junit.Test;

import com.estudohibernateworkstest.EntityManagerTest;

public class JoinTest extends EntityManagerTest {

	@Test
	public void fazerJoin() {
		String jpql = "select p from Pedido p join p.pagamento pag";

		TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

		List<Object[]> lista = typedQuery.getResultList();
		Assert.assertTrue(lista.size() == 3);
	}
}
