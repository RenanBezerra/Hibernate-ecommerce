package com.estudohibernateworkstest.detalhesimportantes;

import java.util.List;

import javax.persistence.EntityGraph;

import org.junit.Assert;
import org.junit.Test;

import com.estudo.hibernate.works.model.Pedido;
import com.estudohibernateworkstest.EntityManagerTest;

public class ProblemaN1Test extends EntityManagerTest {

	@Test
	public void resolverComEntityGraph() {
		EntityGraph<Pedido> entityGraph = entityManager.createEntityGraph(Pedido.class);
		entityGraph.addAttributeNodes("cliente", "notaFiscal", "pagamento");
		
		List<Pedido> lista = entityManager
				.createQuery("select p from Pedido p ", Pedido.class)
				.setHint("javax.persistence.loadgraph", entityGraph)
				.getResultList();
		Assert.assertFalse(lista.isEmpty());
	}
	
	
	@Test
	public void resolverComFetch() {
		List<Pedido> lista = entityManager
				.createQuery("select p from Pedido p "
						+ " join fetch p.cliente c "
						+ " join fetch p.pagamento pag "
						+ " join fetch p.notaFiscal nf", Pedido.class)
				.getResultList();
		
		Assert.assertFalse(lista.isEmpty());
	}
}
