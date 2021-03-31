package com.estudohibernateworkstest.jpql;

import java.util.List;

import javax.persistence.TypedQuery;

import org.junit.Assert;
import org.junit.Test;

import com.estudo.hibernate.works.model.Pedido;
import com.estudohibernateworkstest.EntityManagerTest;

public class OperadoresLogicosTest extends EntityManagerTest {

	@Test
	public void usarOperadores() {
		String jpql = "select p from Pedido p "
				+ " where (p.status = 'AGUARDANDO' or p.status = 'PAGO') and p.total > 100";
		
		TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);
		
		List<Pedido> lista = typedQuery.getResultList();
		Assert.assertFalse(lista.isEmpty());
	}
}
