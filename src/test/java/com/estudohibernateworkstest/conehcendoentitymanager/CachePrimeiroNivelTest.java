package com.estudohibernateworkstest.conehcendoentitymanager;

import org.junit.Test;

import com.estudohibernateworks.model.Produto;
import com.estudohibernateworkstest.EntityManagerTest;

public class CachePrimeiroNivelTest extends EntityManagerTest {

	@Test
	public void verificarCache() {
		Produto produto = entityManager.find(Produto.class, 1);
		System.out.println(produto.getNome());
		
		System.out.println("----------------------------");
		
//		entityManager.close();
//		entityManager = entityManagerFactory.createEntityManager();
		
		Produto produtoResgatado = entityManager.find(Produto.class, produto.getId());
		System.out.println(produtoResgatado.getNome());

	}

}