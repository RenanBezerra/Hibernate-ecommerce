package com.estudohibernateworkstest.jpql;

import java.util.List;

import javax.persistence.TypedQuery;

import org.junit.Assert;
import org.junit.Test;

import com.estudo.hibernate.works.model.Produto;
import com.estudohibernateworkstest.EntityManagerTest;

public class NamedQueryTest extends EntityManagerTest {

	@Test
	public void executarConsulta() {
		TypedQuery<Produto> typedQuery = entityManager
				.createNamedQuery("Produto.listarPorCategoria", Produto.class);
		typedQuery.setParameter("categoria", 2);
		List<Produto> lista = typedQuery.getResultList();
		
		Assert.assertFalse(lista.isEmpty());
	}
	
	//@Test
	public void executarConsultaDinamica() {
		Produto consultado = new Produto();
		consultado.setNome("Câmera GoPro");

		List<Produto> lista = pesquisar(consultado);

		Assert.assertFalse(lista.isEmpty());
		Assert.assertEquals("Câmera GoPro Hero 7", lista.get(0).getNome());
	}

	private List<Produto> pesquisar(Produto consultado) {
		StringBuilder jpql = new StringBuilder("select p from Produto p where 1 = 1");

		if (consultado.getNome() != null) {
			jpql.append(" and p.nome like concat('%', :nome, '%')");
		}
		if (consultado.getDescricao() != null) {
			jpql.append(" and p.descricao like concat('%', :descricao, '%')");

		}
		TypedQuery<Produto> typedQuery = entityManager.createQuery(jpql.toString(), Produto.class);
		if (consultado.getNome() != null) {
			typedQuery.setParameter("nome", consultado.getNome());

		}
		if (consultado.getDescricao() != null) {
			typedQuery.setParameter("descricao", consultado.getDescricao());
		}

		return typedQuery.getResultList();
	}

}
