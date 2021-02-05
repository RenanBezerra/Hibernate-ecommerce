package com.estudoHibernateWorks.iniciandoComJPA;

import com.estudoHibernateWorks.EntityManagerTest;
import com.estudoHibernateWorks.model.Produto;

public class OperacoesComTransacaoTest extends EntityManagerTest{

	
	public void abrirEFecharATransacao() {
//		Produto produto = new Produto();
		
		entityManager.getTransaction().begin();
		
//		entityManager.persist(produto);
//		entityManager.merge(produto);
//		entityManager.remove(produto);
		
		entityManager.getTransaction();
		
		
		
		
	}
}
