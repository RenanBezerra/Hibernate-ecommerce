package com.estudohibernateworks.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.estudohibernateworks.model.Produto;

public class IniciarUnidadeDePersistencia {

	public static void main(String[] args) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Ecommerce-PU");
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		Produto produto = entityManager.find(Produto.class, 1);
		System.out.println("------------ " + produto.getNome());

		entityManager.close();
		entityManagerFactory.close();
	}
}
