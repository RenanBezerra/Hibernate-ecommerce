package com.estudoHibernateWorks.iniciandoComJPA;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.*;


import com.estudoHibernateWorks.model.Produto;

public class ConsultandoRegistrosTest {
	  private static EntityManagerFactory entityManagerFactory;

	    private EntityManager entityManager;

	    @BeforeClass
	    public static void setUpBeforeClass() {
	        entityManagerFactory = Persistence
	                .createEntityManagerFactory("Ecommerce-PU");
	    }

	    @AfterClass
	    public static void tearDownAfterClass() {
	        entityManagerFactory.close();
	    }

	    @Before
	    public void setUp() {
	        entityManager = entityManagerFactory.createEntityManager();
	    }

	    @After
	    public void tearDown() {
	        entityManager.close();
	    }

	    @Test
	    public void busarPorIdentificador() {
	        Produto produto = entityManager.find(Produto.class, 1);
//	        Produto produto = entityManager.getReference(Produto.class, 1);

	        Assert.assertNotNull(produto);
	        Assert.assertEquals("Kindle", produto.getNome());
	    }

	    @Test
	    public void atualizarAReferencia() {
	        Produto produto = entityManager.find(Produto.class, 1);
	        produto.setNome("Microfone Samson");

	        entityManager.refresh(produto);

	        Assert.assertEquals("Kindle", produto.getNome());
	    }
	}