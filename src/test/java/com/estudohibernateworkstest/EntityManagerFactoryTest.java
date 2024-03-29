package com.estudohibernateworkstest;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.AfterClass;
import org.junit.BeforeClass;

public class EntityManagerFactoryTest {

	protected static EntityManagerFactory entityManagerFactory;

	protected static EntityManagerFactory entityManagerfFactory;

	@BeforeClass
	public static void setUpBeforeClass() {
		entityManagerFactory = Persistence.createEntityManagerFactory("Ecommerce-PU");
	}

	@AfterClass
	public static void tearDownAfterClass() {
		entityManagerFactory.close();
	}

	public static void log(Object obj, Object... args) {
		System.out.println(String.format("[LOG " + System.currentTimeMillis() + "] " + obj, args));
	}

	public static void esperar(int segundos) {
		try {
			Thread.sleep(segundos * 1000);
		} catch (InterruptedException e) {

		}
	}

}
