package com.estudohibernateworkstest.criteria;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import org.junit.Assert;
import org.junit.Test;

import com.estudo.hibernate.works.model.ItemPedido;
import com.estudo.hibernate.works.model.Pagamento;
import com.estudo.hibernate.works.model.Pedido;
import com.estudo.hibernate.works.model.Produto;
import com.estudo.hibernate.works.model.StatusPagamento;
import com.estudohibernateworkstest.EntityManagerTest;

public class JoinCriteriaTest extends EntityManagerTest {

	@Test
	public void buscarPedidosComProdutoEspecifico() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
		Root<Pedido> root = criteriaQuery.from(Pedido.class);
		Join<Pedido, ItemPedido> joinPagamento = root.join("itens");
		//root.fetch("itens",JoinType.LEFT);
		
		criteriaQuery.select(root);
		
		//criteriaQuery.where(criteriaBuilder.equal(root.get("id"), 1));
		criteriaQuery.where(criteriaBuilder.equal(joinPagamento.get("produto"),1));
		
		TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);
		List<Pedido> pedidos = typedQuery.getResultList();
		Assert.assertFalse(pedidos.isEmpty());
		pedidos.forEach(p -> System.out.println(p.getId()));
	}
	
	@Test
	public void usarJoinFetch() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
		Root<Pedido> root = criteriaQuery.from(Pedido.class);
		root.fetch("notaFiscal", JoinType.LEFT);
		root.fetch("pagamento", JoinType.LEFT);
		root.fetch("cliente");
		//root.fetch("itens", JoinType.LEFT);
		// Join<Pedido,Cliente> joinCliente =
		// (Join<Pedido,Cliente>)root.<Pedido,Cliente>fetch("cliente");

		criteriaQuery.select(root);

		criteriaQuery.where(criteriaBuilder.equal(root.get("id"), 1));

		TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);

		Pedido pedido = typedQuery.getSingleResult();
		Assert.assertNotNull(pedido);
		Assert.assertFalse(pedido.getItens().isEmpty());
	}

	@Test
	public void fazerLeftOuterJoin() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
		Root<Pedido> root = criteriaQuery.from(Pedido.class);
		Join<Pedido, Pagamento> joinPagamento = root.join("pagamento", JoinType.LEFT);

		criteriaQuery.select(root);

		TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);
		List<Pedido> lista = typedQuery.getResultList();
		Assert.assertTrue(lista.size() == 5);
	}

	@Test
	public void fazerJoinComOn() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
		Root<Pedido> root = criteriaQuery.from(Pedido.class);
		Join<Pedido, Pagamento> joinPagamento = root.join("pagamento");
		joinPagamento.on(criteriaBuilder.equal(joinPagamento.get("status"), StatusPagamento.PROCESSANDO));

		criteriaQuery.select(root);

		TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);
		List<Pedido> lista = typedQuery.getResultList();
		Assert.assertTrue(lista.size() == 2);
	}

	@Test
	public void fazerJoinComVariosJoins() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
		Root<Pedido> root = criteriaQuery.from(Pedido.class);

		Join<Pedido, Pagamento> joinPagamento = root.join("pagamento");
		Join<Pedido, ItemPedido> joinItens = root.join("itens");
		Join<ItemPedido, Produto> joinItemProduto = joinItens.join("produto");

		criteriaQuery.select(joinItemProduto);

		TypedQuery<Produto> typedQuery = entityManager.createQuery(criteriaQuery);
		List<Produto> lista = typedQuery.getResultList();
		lista.forEach(l -> System.out.println(l.getNome()));
		Assert.assertTrue(lista.size() == 5);

	}

	@Test
	public void fazerJoinComSelect() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Pagamento> criteriaQuery = criteriaBuilder.createQuery(Pagamento.class);
		Root<Pedido> root = criteriaQuery.from(Pedido.class);
		Join<Pedido, Pagamento> joinPagamento = root.join("pagamento");

		criteriaQuery.select(joinPagamento);
		criteriaQuery.where(criteriaBuilder.equal(joinPagamento.get("status"), StatusPagamento.PROCESSANDO));

		TypedQuery<Pagamento> typedQuery = entityManager.createQuery(criteriaQuery);
		List<Pagamento> lista = typedQuery.getResultList();
		Assert.assertTrue(lista.size() == 2);
	}

	@Test
	public void fazerJoin() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
		Root<Pedido> root = criteriaQuery.from(Pedido.class);
		Join<Pedido, Pagamento> join = root.join("pagamento");

		criteriaQuery.select(root);

		TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);
		List<Pedido> lista = typedQuery.getResultList();
		Assert.assertTrue(lista.size() == 4);
	}
}
