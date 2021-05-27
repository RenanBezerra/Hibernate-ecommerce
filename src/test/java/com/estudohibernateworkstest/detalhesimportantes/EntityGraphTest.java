package com.estudohibernateworkstest.detalhesimportantes;

import java.util.List;

import javax.persistence.EntityGraph;
import javax.persistence.Subgraph;
import javax.persistence.TypedQuery;

import org.junit.Assert;
import org.junit.Test;

import com.estudo.hibernate.works.model.Cliente;
import com.estudo.hibernate.works.model.Cliente_;
import com.estudo.hibernate.works.model.Pedido;
import com.estudo.hibernate.works.model.Pedido_;
import com.estudohibernateworkstest.EntityManagerTest;

public class EntityGraphTest extends EntityManagerTest {

	@Test
	public void buscarAtributosEssenciaisComNameEntityGraph() {
		EntityGraph<?> entityGraph = entityManager.createEntityGraph("Pedido.dadosEssenciais");
		entityGraph.addAttributeNodes("pagamento");

		TypedQuery<Pedido> typedQuery = entityManager.createQuery("select p from Pedido p", Pedido.class);
		typedQuery.setHint("javax.persistence.fetchgraph", entityGraph);
		List<Pedido> lista = typedQuery.getResultList();
		Assert.assertFalse(lista.isEmpty());
	}

	@Test
	public void buscarAtributosEssenciaisDePedidos03() {
		EntityGraph<Pedido> entityGraph = entityManager.createEntityGraph(Pedido.class);
		entityGraph.addAttributeNodes(Pedido_.dataCriacao, Pedido_.status, Pedido_.total);

		Subgraph<Cliente> subgraphCliente = entityGraph.addSubgraph(Pedido_.cliente);
		subgraphCliente.addAttributeNodes(Cliente_.nome, Cliente_.cpf);

		TypedQuery<Pedido> typedQuery = entityManager.createQuery("select p from Pedido p", Pedido.class);
		typedQuery.setHint("javax.persistence.fetchgraph", entityGraph);
		List<Pedido> lista = typedQuery.getResultList();
		Assert.assertFalse(lista.isEmpty());
	}

	@Test
	public void buscarAtributosEssenciaisDePedidos02() {
		EntityGraph<Pedido> entityGraph = entityManager.createEntityGraph(Pedido.class);
		entityGraph.addAttributeNodes("dataCriacao", "status", "total");

		Subgraph<Cliente> subgraphCliente = entityGraph.addSubgraph("cliente", Cliente.class);
		subgraphCliente.addAttributeNodes("nome", "cpf");

		TypedQuery<Pedido> typedQuery = entityManager.createQuery("select p from Pedido p", Pedido.class);
		typedQuery.setHint("javax.persistence.fetchgraph", entityGraph);
		List<Pedido> lista = typedQuery.getResultList();
		Assert.assertFalse(lista.isEmpty());
	}

	@Test
	public void buscarAtributosEssenciaisDePedido() {

		EntityGraph<Pedido> entityGraph = entityManager.createEntityGraph(Pedido.class);
		entityGraph.addAttributeNodes("dataCriacao", "status", "total", "notaFiscal", "cliente");

		/*
		 * Map<String, Object> properties = new HashMap<>();
		 * //properties.put("javax.persistence.fetchgraph", entityGraph);
		 * properties.put("javax.persistence.loadgraph", entityGraph);
		 * 
		 * Pedido pedido = entityManager.find(Pedido.class, 1, properties);
		 * Assert.assertNotNull(pedido);
		 */

		TypedQuery<Pedido> typedQuery = entityManager.createQuery("select p from Pedido p", Pedido.class);
		typedQuery.setHint("javax.persistence.fetchgraph", entityGraph);
		List<Pedido> lista = typedQuery.getResultList();

		Assert.assertFalse(lista.isEmpty());
	}
}
