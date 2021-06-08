package com.estudohibernateworkstest.multitenant;

import javax.persistence.EntityManager;

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
	}
}
