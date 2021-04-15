package com.estudohibernateworkstest.criteria;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.junit.Assert;
import org.junit.Test;

import com.estudo.hibernate.works.model.Pedido;
import com.estudohibernateworkstest.EntityManagerTest;

public class BasicoCriteriaTest extends EntityManagerTest{

	@Test
	public void buscaPorIdentificador() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
		Root<Pedido> root = criteriaQuery.from(Pedido.class);
		
		criteriaQuery.select(root);
		
		criteriaQuery.where(criteriaBuilder.equal(root.get("id"), 1));
		
		TypedQuery<Pedido> typedQuery = entityManager
				.createQuery(criteriaQuery);
		
		Pedido pedido = typedQuery.getSingleResult();
		Assert.assertNotNull(pedido);
		
		
	}
}
