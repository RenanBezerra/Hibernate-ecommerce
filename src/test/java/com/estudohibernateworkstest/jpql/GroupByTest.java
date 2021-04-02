package com.estudohibernateworkstest.jpql;

import java.util.List;

import javax.persistence.TypedQuery;

import org.junit.Assert;
import org.junit.Test;

import com.estudohibernateworkstest.EntityManagerTest;

public class GroupByTest extends EntityManagerTest {

	@Test
	public void agruparResultado() {
		//Quantidade de produtos
//		String jpql = "select c.nome, count(p.id) from Categoria c join c.produtos p group by c.id";
	
		// Total de vendas por mes
//		String jpql = "select concat(year(p.dataCriacao), ' / ' , function('monthname', p.dataCriacao)), sum(p.total)"
//				+ " from Pedido p group by year(p.dataCriacao), month(p.dataCriacao) ";

		// Total de vendas por categoria
		String jpql = "select c.nome, sum(ip.precoProduto) from ItemPedido ip "
				+ " join ip.produto pro join pro.categorias c "
				+ " group by c.id";
		
		TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);
		
		List<Object[]> lista = typedQuery.getResultList();
		
		Assert.assertFalse(lista.isEmpty());
		lista.forEach(arr -> System.out.println("GroupBy: " +arr[0] + ", " + arr[1]));
	}
}
