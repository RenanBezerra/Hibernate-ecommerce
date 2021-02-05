package com.estudoHibernateWorks.iniciandoComJPA;


import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

import com.estudoHibernateWorks.EntityManagerTest;
import com.estudoHibernateWorks.model.Produto;

public class OperacoesComTransacaoTest extends EntityManagerTest{

	@Test
	public void inserirOPrimeiroObjeto() {
		Produto produto = new Produto();
		
		produto.setId(2);
		produto.setNome("Camera Canon");
		produto.setDescricao("A melhor definicao para suas fotos" );
		produto.setPreco(new BigDecimal(5000));
		
		entityManager.getTransaction().begin();
		entityManager.persist(produto);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Produto produtoVerificacao = entityManager.find(Produto.class, produto.getId());
		Assert.assertNotNull(produtoVerificacao);
		Assert.assertEquals("Camera Canon", produtoVerificacao.getNome());
		
		
	}
	
	@Test
	public void abrirEFecharATransacao() {
//		Produto produto = new Produto();
		
		entityManager.getTransaction().begin();
		
//		entityManager.persist(produto);
//		entityManager.merge(produto);
//		entityManager.remove(produto);
		
		entityManager.getTransaction();
		
		
		
		
	}
}
