package com.estudohibernateworkstest.jpql;

import java.util.List;

import javax.persistence.TypedQuery;

import org.junit.Assert;
import org.junit.Test;

import com.estudohibernateworkstest.EntityManagerTest;

public class FuncoesTest extends EntityManagerTest{

	@Test
	public void aplicarFuncaoColecao() {
		String jpql = "select size(p.itens) from Pedido p where size(p.itens) > 1";
		
		TypedQuery<Integer> typedQuery = entityManager.createQuery(jpql, Integer.class);
		
		List<Integer> lista = typedQuery.getResultList();
		Assert.assertFalse(lista.isEmpty());
		
		lista.forEach( size -> System.out.println("funcao colecao : " +size));
	}
	
	@Test
	public void aplicarFuncaoNumero() {
//		String jpql = "select abs(-10), mod(3,2), sqrt(9) from Pedido";
		String jpql = "select abs(p.total), mod(p.id,2), sqrt(p.total) from Pedido p "
				+ "where abs(p.total) > 1000";
		
		TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);
		
		List<Object[]> lista = typedQuery.getResultList();
		Assert.assertFalse(lista.isEmpty());
		
		lista.forEach(arr -> System.out.println("Funcoes numeros : " + arr[0] + " | " + arr[1] + " | " + arr[2]));
	}
	
	
	@Test
	public void aplicarFuncaoData() {
		// TimeZone.setDefault(TimeZone.getTimeZone("UTC"));  codigo para alterar a data conforme o banco anotar em uma classe com @postCoonstructor
		
		String jpql = "select current_date, current_time, current_timestamp from Pedido p";
//		String jpql = "select year(p.dataCriacao), month(p.dataCriacao), day(p.dataCriacao) from Pedido p";
//		String jpql = "select hour(p.dataCriacao), minute(p.dataCriacao), second(p.dataCriacao) from Pedido p";
		
		TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);
		
		List<Object[]> lista = typedQuery.getResultList();
		Assert.assertFalse(lista.isEmpty());
		
		lista.forEach(arr -> System.out.println("Data: "+arr[0] + " | " + arr[1] + " | " + arr[2]));
	}
	
	@Test
	public void aplicarFuncaoString() {
		// concat, length, locale, substring, lower, upper, trim;
		
//		String jpql = "select c.nome, concat('Categoria: ', c.nome) from Categoria c ";
//		String jpql = "select c.nome, length(c.nome) from Categoria c ";
//		String jpql = "select c.nome, locate('a', c.nome) from Categoria c ";
//		String jpql = "select c.nome, substring(c.nome, 1, 2) from Categoria c ";
//		String jpql = "select c.nome, lower(c.nome) from Categoria c ";
//		String jpql = "select c.nome, upper(c.nome) from Categoria c ";
//		String jpql = "select c.nome, trim(c.nome) from Categoria c ";
//		String jpql = "select c.nome, length(c.nome) from Categoria c where length(c.nome) > 10 ";
		String jpql = "select c.nome, length(c.nome) from Categoria c where substring(c.nome,1,1) = 'N' ";
		
		
		TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);
		
		List<Object[]> lista = typedQuery.getResultList();
		Assert.assertFalse(lista.isEmpty());
		
		lista.forEach(arr -> System.out.println(arr[0] + " - " + arr[1]));
	}
}
