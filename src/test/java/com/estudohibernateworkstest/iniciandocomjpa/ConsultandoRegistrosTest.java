package com.estudohibernateworkstest.iniciandocomjpa;

import org.junit.Assert;
import org.junit.Test;

import com.estudohibernateworks.model.Produto;
import com.estudohibernateworkstest.EntityManagerTest;

public class ConsultandoRegistrosTest extends EntityManagerTest {

	@Test
	public void busarPorIdentificador() {
		Produto produto = entityManager.find(Produto.class, 1);
//	        Produto produto = entityManager.getReference(Produto.class, 1);

		System.out.println(">>>>>>>>>>" + produto.getNome());
		Assert.assertNotNull(produto);
		Assert.assertEquals("Kindle", produto.getNome());
	}

	@Test
	public void atualizarAReferencia() {
		Produto produto = entityManager.find(Produto.class, 1);
		produto.setNome("Microfone Samson");

		System.out.println(">>>>>>>>>>" + produto.getNome());
		entityManager.refresh(produto);

		Assert.assertEquals("Kindle", produto.getNome());
	}
}