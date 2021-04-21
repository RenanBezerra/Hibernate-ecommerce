package com.estudohibernateworkstest.criteria;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.junit.Assert;
import org.junit.Test;

import com.estudo.hibernate.works.model.Cliente;
import com.estudo.hibernate.works.model.Cliente_;
import com.estudo.hibernate.works.model.Pedido;
import com.estudo.hibernate.works.model.Pedido_;
import com.estudo.hibernate.works.model.Produto;
import com.estudo.hibernate.works.model.Produto_;
import com.estudohibernateworkstest.EntityManagerTest;

public class ExpressoesCondicionaisCriteriaTest extends EntityManagerTest {

	@Test
	public void usarBetween() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
		Root<Pedido> root = criteriaQuery.from(Pedido.class);
		
		criteriaQuery.select(root);
		
		criteriaQuery.where(criteriaBuilder.between(
				root.get(Pedido_.dataCriacao), LocalDateTime.now().minusDays(5).withSecond(0).withMinute(0).withHour(0), LocalDateTime.now()));
		
		
//		criteriaQuery.where(criteriaBuilder.between(
//				root.get(Pedido_.total), new BigDecimal(499), new BigDecimal(2399)));
		
		TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);
		List<Pedido> lista = typedQuery.getResultList();
		Assert.assertFalse(lista.isEmpty());
		
		lista.forEach(p -> System.out.println(
				"ID: " + p.getId() + ", Total: " + p.getTotal()));
	}
	
	@Test
	public void usarMaiorMenorComDatas() {
		//todos os pedidos que foram cadastrados nos ultimos 3 dias
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
		Root<Pedido> root = criteriaQuery.from(Pedido.class);
		
		criteriaQuery.select(root);
		
		criteriaQuery.where(criteriaBuilder.greaterThan(
				root.get(Pedido_.DATA_CRIACAO), LocalDateTime.now().minusDays(3)));
		
		TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);
		List<Pedido> lista = typedQuery.getResultList();
		lista.forEach(p -> System.out.println(p.getId()));
		Assert.assertFalse(lista.isEmpty());
	}
	
	@Test
	public void usarMaiorMenor() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
		Root<Produto> root = criteriaQuery.from(Produto.class);
		
		criteriaQuery.select(root);
		
		criteriaQuery.where(criteriaBuilder.greaterThanOrEqualTo(
				root.get(Produto_.preco), new BigDecimal(799)),
		criteriaBuilder.lessThanOrEqualTo(
				root.get(Produto_.preco), new BigDecimal(3500)));
		
		TypedQuery<Produto> typedQuery = entityManager.createQuery(criteriaQuery);
		List<Produto> lista = typedQuery.getResultList();
		Assert.assertFalse(lista.isEmpty());
		
		lista.forEach(p -> System.out.println("ID: " + p.getId() + ", Nome: " + p.getNome() + ", Preço: " + p.getPreco()));
		
	
	}
	
	
	@Test
	public void usarExpressaoCondcionalLike() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Cliente> criteriaQuery = criteriaBuilder.createQuery(Cliente.class);
		Root<Cliente> root = criteriaQuery.from(Cliente.class);
		
		criteriaQuery.select(root);
		
		criteriaQuery.where(criteriaBuilder.like(root.get(Cliente_.nome), "%a%"));
		
		TypedQuery<Cliente> typedQuery = entityManager.createQuery(criteriaQuery);
		List<Cliente> lista = typedQuery.getResultList();
		Assert.assertFalse(lista.isEmpty());
	}
	
	@Test
	public void usarIsNull() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
		Root<Produto> root = criteriaQuery.from(Produto.class);
		
		criteriaQuery.select(root);
		
		criteriaQuery.where(criteriaBuilder.isNull(root.get(Produto_.foto)));
		//criteriaQuery.where(criteriaBuilder.isNotNull(root.get(Produto_.foto)));
		//criteriaQuery.where(root.get(Produto_.foto).isNull());
		
		TypedQuery<Produto> typedQuery = entityManager.createQuery(criteriaQuery);
		List<Produto> lista = typedQuery.getResultList();
		Assert.assertFalse(lista.isEmpty());
	}
	
	@Test
	public void usarIsEmpty() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
		Root<Produto> root = criteriaQuery.from(Produto.class);
		
		criteriaQuery.select(root);
		
		criteriaQuery.where(criteriaBuilder.isNotEmpty(root.get(Produto_.categorias)));
		
		TypedQuery<Produto> typedQuery = entityManager.createQuery(criteriaQuery);
		List<Produto> lista = typedQuery.getResultList();
		Assert.assertFalse(lista.isEmpty());
	}
}
