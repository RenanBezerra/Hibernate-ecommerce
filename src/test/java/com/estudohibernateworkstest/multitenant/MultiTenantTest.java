package com.estudohibernateworkstest.multitenant;

import javax.persistence.EntityManager;

import org.junit.Assert;
import org.junit.Test;

import com.estudo.hibernate.works.model.Produto;
import com.estudo.hibernate.works.tenant.EcmCurrentTenantIdentifierResolver;
import com.estudohibernateworkstest.EntityManagerFactoryTest;

public class MultiTenantTest extends EntityManagerFactoryTest {

	@Test
	public void usarAbordagemPorSchema() {
		EcmCurrentTenantIdentifierResolver.setTenantIdentifier("hibernateworks_ecommerce");
		EntityManager entityManager1 = entityManagerFactory.createEntityManager();
		Produto produto1 = entityManager1.find(Produto.class, 1);
		Assert.assertEquals("Kindle", produto1.getNome());
		entityManager1.close();
		
		EcmCurrentTenantIdentifierResolver.setTenantIdentifier("loja_ecommerce");
		EntityManager entityManager2 = entityManagerFactory.createEntityManager();
		Produto produto2 = entityManager2.find(Produto.class, 1);
		Assert.assertEquals("Kindle Paperwhite", produto2.getNome());
		entityManager2.close();
		
		
	}
}
