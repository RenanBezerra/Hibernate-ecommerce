package com.estudohibernateworkstest.cache;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Cache;
import javax.persistence.CacheStoreMode;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.estudo.hibernate.works.model.Pedido;

public class CacheTest {

	protected static EntityManagerFactory entityManagerFactory;

	@BeforeClass
	public static void setUpBeforeClass() {
		entityManagerFactory = Persistence.createEntityManagerFactory("Ecommerce-PU");

	}

	@AfterClass
	public static void tearDownAfterClass() {
		entityManagerFactory.close();
	}

	public static void esperar(int segundos) {
		try {
			Thread.sleep(segundos * 1000);

		} catch (InterruptedException e) {

		}
	}

	private void log(Object obj) {

		System.out.println("[LOG " + System.currentTimeMillis() + "] " + obj);
	}

	@Test
	public void ehcache() {
		Cache cache = entityManagerFactory.getCache();

		EntityManager entityManager1 = entityManagerFactory.createEntityManager();
		EntityManager entityManager2 = entityManagerFactory.createEntityManager();

		log("Buscando e incluindo no cache...");
		entityManager1.createQuery("select p from Pedido p", Pedido.class).getResultList();
		log("------");

		esperar(1);
		Assert.assertTrue(cache.contains(Pedido.class, 2));
		entityManager2.find(Pedido.class, 2);

		esperar(3);
		Assert.assertFalse(cache.contains(Pedido.class, 2));

		entityManager1.close();
		entityManager2.close();
	}

	@Test
	public void controleCacheDinamiamente() {
//		javax.persistence.cache.retrieveMode CacheRetrieveMode
//		javax.persistence.cache.storeMode CacheStoreMode

		Cache cache = entityManagerFactory.getCache();

		System.out.println("Buscando todos os pedidos.............................");
		EntityManager entityManager1 = entityManagerFactory.createEntityManager();
		entityManager1.setProperty("javax.persistence.cache.storeMode", CacheStoreMode.BYPASS);
		entityManager1.createQuery("select p from Pedido p", Pedido.class)
				.setHint("javax.persistence.cache.storeMode", CacheStoreMode.USE).getResultList();

		System.out.println("Buscando o pedido da ID igual a 2 ........................");
		EntityManager entityManager2 = entityManagerFactory.createEntityManager();
		Map<String, Object> propriedades = new HashMap<>();
		// propriedades.put("javax.persistence.cache.storeMode", CacheStoreMode.BYPASS);
		// propriedades.put("javax.persistence.cache.retrieveMode",
		// CacheStoreMode.BYPASS);

		entityManager2.find(Pedido.class, 2, propriedades);

		System.out.println("Buscando todos os pedidos (de novo)..........................");
		EntityManager entityManager3 = entityManagerFactory.createEntityManager();
		entityManager3.createQuery("select p from Pedido p", Pedido.class)
				// .setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS)
				.getResultList();

		entityManager1.close();
		entityManager2.close();
		entityManager3.close();
	}

	@Test
	public void analisarOpcoesCache() {
		Cache cache = entityManagerFactory.getCache();

		EntityManager entityManager1 = entityManagerFactory.createEntityManager();

		System.out.println("Buscando a partir da instancia 1: ");
		entityManager1.createQuery("select p from Pedido p", Pedido.class).getResultList();

		Assert.assertTrue(cache.contains(Pedido.class, 1));
		entityManager1.close();
	}

	@Test
	public void verificarSeEstaNoCache() {
		Cache cache = entityManagerFactory.getCache();

		EntityManager entityManager1 = entityManagerFactory.createEntityManager();

		System.out.println("Buscando a partir da instancia 1: ");
		entityManager1.createQuery("select p from Pedido p", Pedido.class).getResultList();

		Assert.assertTrue(cache.contains(Pedido.class, 1));
		Assert.assertTrue(cache.contains(Pedido.class, 2));

		entityManager1.close();
	}

	@Test
	public void removerDoCache() {
		Cache cache = entityManagerFactory.getCache();

		EntityManager entityManager1 = entityManagerFactory.createEntityManager();
		EntityManager entityManager2 = entityManagerFactory.createEntityManager();

		System.out.println("Buscando a partir da instancia 1: ");
		entityManager1.createQuery("select p from Pedido p", Pedido.class).getResultList();

		System.out.println("Removendo do cache");
		cache.evictAll();
		cache.evict(Pedido.class);

		System.out.println("Buscando a partir da instancia 2: ");
		entityManager2.find(Pedido.class, 1);
		entityManager2.find(Pedido.class, 2);

		entityManager1.close();
		entityManager2.close();

	}

	@Test
	public void adicionarPedidosNoCache() {
		EntityManager entityManager1 = entityManagerFactory.createEntityManager();
		EntityManager entityManager2 = entityManagerFactory.createEntityManager();

		System.out.println("Buscando a partir da instancia 1: ");
		entityManager1.createQuery("select p from Pedido p ", Pedido.class).getResultList();

		System.out.println("Buscando a partir da instancia 2:");
		entityManager2.find(Pedido.class, 1);

		entityManager1.close();
		entityManager2.close();
	}

	@Test
	public void buscarDoCache() {
		EntityManager entityManager1 = entityManagerFactory.createEntityManager();
		EntityManager entityManager2 = entityManagerFactory.createEntityManager();

		System.out.println("Buscando a partir da instancia 1:");
		entityManager1.find(Pedido.class, 1);

		System.out.println("Buscando a partir da instancia 1");
		entityManager2.find(Pedido.class, 1);

		entityManager1.close();
		entityManager2.close();
	}

}
