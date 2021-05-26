package com.estudohibernateworkstest.detalhesimportantes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityGraph;
import javax.persistence.TypedQuery;

import org.junit.Assert;
import org.junit.Test;

import com.estudo.hibernate.works.model.Pedido;
import com.estudohibernateworkstest.EntityManagerTest;

public class EntityGraphTest extends EntityManagerTest {

	@Test
	public void buscarAtributosEssenciaisDePedido() {
		
		EntityGraph<Pedido> entityGraph = entityManager.createEntityGraph(Pedido.class);
		entityGraph.addAttributeNodes("dataCriacao", "status", "total","notaFiscal", "cliente");
		
		/*
		 * Map<String, Object> properties = new HashMap<>();
		 * //properties.put("javax.persistence.fetchgraph", entityGraph);
		 * properties.put("javax.persistence.loadgraph", entityGraph);
		 * 
		 * Pedido pedido = entityManager.find(Pedido.class, 1, properties);
		 * Assert.assertNotNull(pedido);
		 */
		
		TypedQuery<Pedido> typedQuery = entityManager
				.createQuery("select p from Pedido p", Pedido.class);
		typedQuery.setHint("javax.persistence.fetchgraph", entityGraph);
		List<Pedido> lista = typedQuery.getResultList();
		
		Assert.assertFalse(lista.isEmpty());
	}
}
