package com.estudohibernateworkstest.mapeamentobasico;

import org.junit.Assert;
import org.junit.Test;

import com.estudo.hibernate.works.model.Categoria;
import com.estudohibernateworkstest.EntityManagerTest;

public class EstrategiaChavePrimariaTest extends EntityManagerTest {

	@Test
	public void testarEstrategiaChave() {

		Categoria categoria = new Categoria();
		categoria.setNome("Natacao");

		entityManager.getTransaction().begin();
		entityManager.persist(categoria);
		entityManager.getTransaction().commit();

		entityManager.clear();

		Categoria categoriaVerificacao = entityManager.find(Categoria.class, categoria.getId());
		Assert.assertNotNull(categoriaVerificacao);
	}
}
