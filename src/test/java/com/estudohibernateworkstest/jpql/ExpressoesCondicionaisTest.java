package com.estudohibernateworkstest.jpql;

import java.util.List;

import javax.persistence.TypedQuery;

import org.junit.Assert;
import org.junit.Test;

import com.estudohibernateworkstest.EntityManagerTest;

public class ExpressoesCondicionaisTest extends EntityManagerTest {

	@Test
	public void usarIsNull() {
	
		String jpql = "select p from Produto p where p.foto is null";
		
		TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);
		
		List<Object[]> lista = typedQuery.getResultList();
		Assert.assertFalse(lista.isEmpty());
	}
	
	@Test
	public void usarIsEmpty() {
		String jpql = "select p from Produto p where p.categorias is empty";
		
		TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);
		
		List<Object[]> lista = typedQuery.getResultList();
		Assert.assertFalse(lista.isEmpty());
	}
	@Test
	public void usarExpressaoCondicionalLike() {
		String jpql = "select c from Cliente c where c.nome like concat('%' , :nome, '%')";
		
		TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);
		typedQuery.setParameter("nome", "a");
		
		List<Object[]> lista = typedQuery.getResultList();
		Assert.assertFalse(lista.isEmpty());
	}
}
