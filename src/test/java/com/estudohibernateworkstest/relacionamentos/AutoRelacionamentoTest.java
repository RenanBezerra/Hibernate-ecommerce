package com.estudohibernateworkstest.relacionamentos;

import org.junit.Assert;
import org.junit.Test;

import com.estudohibernateworks.model.Categoria;
import com.estudohibernateworkstest.EntityManagerTest;

public class AutoRelacionamentoTest extends EntityManagerTest {

	@Test
	public void verificarRelacionamento() {
		Categoria categoriaPai = new Categoria();
		categoriaPai.setNome("Eletronicos");

		Categoria categoria = new Categoria();
		categoria.setNome("Celulares");
		categoria.setCategoriaPai(categoriaPai);

		entityManager.getTransaction().begin();
		entityManager.persist(categoriaPai);
		entityManager.persist(categoria);
		entityManager.getTransaction().commit();

		entityManager.clear();

		Categoria categoriaVerificacao = entityManager.find(Categoria.class, categoria.getId());
		Assert.assertNotNull(categoriaVerificacao.getCategoriaPai());

		Categoria categoriaPaiVerificacao = entityManager.find(Categoria.class, categoriaPai.getId());
		Assert.assertFalse(categoriaPaiVerificacao.getCategorias().isEmpty());

	}

}
