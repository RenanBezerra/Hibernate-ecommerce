package com.estudoHibernateWorks.iniciandoComJPA;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

import com.estudoHibernateWorks.EntityManagerTest;
import com.estudoHibernateWorks.model.Produto;

public class OperacoesComTransacaoTest extends EntityManagerTest {

	@Test
	public void atualizarObjetoGerenciado() {

		Produto produto = entityManager.find(Produto.class, 1);

		entityManager.getTransaction().begin();
		produto.setNome("Kindle Paperwhite 2° Geracao");
		entityManager.getTransaction().commit();

		entityManager.clear();

		Produto produtoVerificacao = entityManager.find(Produto.class, produto.getId());
		Assert.assertEquals("Kindle Paperwhite 2° Geracao", produtoVerificacao.getNome());
	}

	@Test
	public void atualizacaorObjeto() {
		Produto produto = new Produto();

		produto.setId(1);
		produto.setNome("Kindle Paperwhite");
		produto.setDescricao("Conheca o novo Kindle");
		produto.setPreco(new BigDecimal(599));

		entityManager.getTransaction().begin();
		entityManager.merge(produto);
		entityManager.getTransaction().commit();

		entityManager.clear();

		Produto produtoVerificacao = entityManager.find(Produto.class, produto.getId());
		Assert.assertNotNull(produtoVerificacao);
		Assert.assertEquals("Kindle Paperwhite", produtoVerificacao.getNome());

	}

	@Test
	public void removerObject() {
		Produto produto = entityManager.find(Produto.class, 3);

		entityManager.getTransaction().begin();
		entityManager.remove(produto);
		entityManager.getTransaction().commit();

//		entityManager.clear(); Não é necessario na asserção para operacao de remocao

		Produto produtoVerificado = entityManager.find(Produto.class, 3);
		Assert.assertNull(produtoVerificado);
	}

	@Test
	public void inserirOPrimeiroObjeto() {
		Produto produto = new Produto();

		produto.setId(2);
		produto.setNome("Camera Canon");
		produto.setDescricao("A melhor definicao para suas fotos");
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
