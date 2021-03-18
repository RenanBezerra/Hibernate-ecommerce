package com.estudohibernateworkstest.relacionamentos;

import org.junit.Assert;
import org.junit.Test;

import com.estudo.hibernate.works.model.Categoria;
import com.estudo.hibernate.works.model.Pedido;
import com.estudohibernateworkstest.EntityManagerTest;

public class AutoRelacionamentoTest extends EntityManagerTest {

	@Test
	public void verificarRelacionamento() {
		Categoria categoriaPai = new Categoria();
		categoriaPai.setNome("Futebol");

		Categoria categoria = new Categoria();
		categoria.setNome("Uniformes");
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

	@Test
	public void removerEntidadeRelacionada() {
		Pedido pedido = entityManager.find(Pedido.class, 1);

		Assert.assertFalse(pedido.getItens().isEmpty());

		entityManager.getTransaction().begin();
		pedido.getItens().forEach(i -> entityManager.remove(i));
		entityManager.remove(pedido);
		entityManager.getTransaction().commit();

		entityManager.clear();

		Pedido pedidoVerificacao = entityManager.find(Pedido.class, 1);
		Assert.assertNull(pedidoVerificacao);
	}

}
