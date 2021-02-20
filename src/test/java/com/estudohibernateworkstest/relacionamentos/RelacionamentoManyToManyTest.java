package com.estudohibernateworkstest.relacionamentos;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import com.estudohibernateworks.model.Categoria;
import com.estudohibernateworks.model.Produto;
import com.estudohibernateworkstest.EntityManagerTest;

public class RelacionamentoManyToManyTest extends EntityManagerTest {

	@Test
	public void verificarRelacionamento() {
		Produto produto = entityManager.find(Produto.class, 1);
		Categoria categoria = entityManager.find(Categoria.class, 1);

		entityManager.getTransaction().begin();
		// categoria.setProduto(Arrays.asList(produto));
		produto.setCategorias(Arrays.asList(categoria));
		entityManager.getTransaction().commit();

		entityManager.clear();

		Categoria categoriaVerificacao = entityManager.find(Categoria.class, categoria.getId());
		Assert.assertFalse(categoriaVerificacao.getProdutos().isEmpty());

	}
}
