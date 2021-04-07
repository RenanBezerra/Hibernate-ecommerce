package com.estudohibernateworkstest.jpql;

import java.util.List;

import javax.persistence.TypedQuery;

import org.junit.Assert;
import org.junit.Test;

import com.estudo.hibernate.works.model.Cliente;
import com.estudo.hibernate.works.model.Pedido;
import com.estudo.hibernate.works.model.Produto;
import com.estudohibernateworkstest.EntityManagerTest;

public class SubqueriesTest extends EntityManagerTest{

	@Test
	public void pesquisarComIn() {
		String jpql = "select p from Pedido p where p.id in "
				+ "(select p2.id from ItemPedido i2 "
				+ "join i2.pedido p2 join i2.produto pro2 where pro2.preco > 100)";
		
		TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);
		
		List<Pedido> lista =typedQuery.getResultList();
		Assert.assertFalse(lista.isEmpty());
		
		lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
	}
	
	@Test
	public void pesquisarSubQueries() {
		// Bons clientes. Versao 2.
		//String jpql = "";
		
		// Bons clientes. Versao 2.
		String jpql = "select c from Cliente c where "
				+ " 500 < (select sum(p.total) from Pedido p where p.cliente = c)";
		
//		String jpql = "select c from Cliente c where "
//				+ " 500 < (select sum(p.total) from c.pedidos p)";
//		
		
		// Bons clientes. Versao 2.
//		String jpql = "select p from Pedido p where "
//				+ " p.total > (select avg(total) from Pedido)";
		
		// o produto ou os produtos mais caros da base.	
//	String jpql = "select p from Produto p where "
//			+ "p.preco = (select max(preco) from Produto)";
	
	TypedQuery<Cliente> typedQuery = entityManager.createQuery(jpql, Cliente.class);
	
	List<Cliente> lista = typedQuery.getResultList();
	Assert.assertFalse(lista.isEmpty());
	
	lista.forEach(obj -> System.out.println("ID: " + obj.getId() + ", Preco: "+ obj.getNome()));
	}
}