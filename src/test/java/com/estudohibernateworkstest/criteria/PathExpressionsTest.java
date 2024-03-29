package com.estudohibernateworkstest.criteria;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import org.junit.Assert;
import org.junit.Test;

import com.estudo.hibernate.works.model.Cliente_;
import com.estudo.hibernate.works.model.ItemPedido;
import com.estudo.hibernate.works.model.ItemPedido_;
import com.estudo.hibernate.works.model.Pedido;
import com.estudo.hibernate.works.model.Pedido_;
import com.estudo.hibernate.works.model.Produto_;
import com.estudohibernateworkstest.EntityManagerTest;

public class PathExpressionsTest extends EntityManagerTest {

	@Test
	public void buscarPedidosComProdutoDeIDIgual1() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
		Root<Pedido> root = criteriaQuery.from(Pedido.class);
		Join<Pedido, ItemPedido> joinItemPedido = root.join(Pedido_.itens);

		criteriaQuery.select(root);

		criteriaQuery.where(criteriaBuilder.equal(joinItemPedido.get(ItemPedido_.produto).get(Produto_.id), 1));

		TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);
		List<Pedido> lista = typedQuery.getResultList();

		Assert.assertFalse(lista.isEmpty());

	}

	@Test
	public void usarPathExpression() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
		Root<Pedido> root = criteriaQuery.from(Pedido.class);

		criteriaQuery.select(root);

		criteriaQuery.where(criteriaBuilder.like(root.get(Pedido_.cliente).get(Cliente_.nome), "M%"));

		TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);
		List<Pedido> lista = typedQuery.getResultList();

		Assert.assertFalse(lista.isEmpty());
	}
}
