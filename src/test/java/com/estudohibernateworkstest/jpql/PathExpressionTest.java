package com.estudohibernateworkstest.jpql;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import javax.persistence.TypedQuery;

import org.junit.Assert;
import org.junit.Test;

import com.estudo.hibernate.works.model.Pedido;
import com.estudohibernateworkstest.EntityManagerTest;

public class PathExpressionTest extends EntityManagerTest {

	@Test
	public void buscarPedidosComProdutoEspecifico() {
		String jpql = "select p from Pedido p "
				+ "join p.itens ite "
				+ "where ite.id.produtoId = 1";
		
		TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);
		
		List<Pedido> lista = typedQuery.getResultList();
		
	
		for (Pedido pedido : lista) {
		 pedido.getItens().forEach(arr -> System.out.println(arr.getProduto().getNome()));
		}
		
		lista.forEach(arr -> System.out.println("test"+ arr.getItens().get(0) + ", " + arr.getStatus()));
		
		Assert.assertTrue(lista.size() == 2);
		Assert.assertEquals("Kindle", lista.get(0).getItens().get(0).getProduto().getNome());
	}
	
	@Test
	public void usarPathExpressions() {
		String jpql = "select p.cliente.nome from Pedido p";
		
		TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);
		
		List<Object[]> lista = typedQuery.getResultList();
		Assert.assertFalse(lista.isEmpty());
	}
}
