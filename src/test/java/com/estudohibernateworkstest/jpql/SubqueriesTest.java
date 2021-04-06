package com.estudohibernateworkstest.jpql;

import java.util.List;

import javax.persistence.TypedQuery;

import org.junit.Assert;
import org.junit.Test;

import com.estudohibernateworkstest.EntityManagerTest;

public class SubqueriesTest extends EntityManagerTest{

	@Test
	public void pesquisarSubQueries() {
		// Bons clientes. Versao 2.
		//String jpql = "";
		
		// Bons clientes. Versao 2.
		//String jpql = "";
		
		// Bons clientes. Versao 2.
		//String jpql = "";
		
		// o produto ou os produtos mais caros da base.
		
	String jpql = "";
	TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);
	
	List<Object[]> lista = typedQuery.getResultList();
	Assert.assertFalse(lista.isEmpty());
	}
}
