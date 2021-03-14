package com.estudohibernateworkstest.conehcendoentitymanager;

import org.junit.Test;

import com.estudohibernateworks.model.Categoria;
import com.estudohibernateworkstest.EntityManagerTest;

public class EstadosECiclosDeVidaTest extends EntityManagerTest {

	@Test
	public void analisarEstados() {
		Categoria categoriaNovo = new Categoria();
		categoriaNovo.setNome("Eletronicos");

		Categoria categoriaGerenciadaMerge = entityManager.merge(categoriaNovo);

		Categoria categoriaGerenciada = entityManager.find(Categoria.class, 1);

		entityManager.remove(categoriaGerenciada);
		entityManager.persist(categoriaGerenciada);

		entityManager.detach(categoriaGerenciada);

	}

}