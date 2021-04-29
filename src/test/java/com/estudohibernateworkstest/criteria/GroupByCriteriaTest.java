package com.estudohibernateworkstest.criteria;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import org.junit.Test;

import com.estudo.hibernate.works.model.Categoria;
import com.estudo.hibernate.works.model.Categoria_;
import com.estudo.hibernate.works.model.ItemPedido;
import com.estudo.hibernate.works.model.ItemPedido_;
import com.estudo.hibernate.works.model.Produto;
import com.estudo.hibernate.works.model.Produto_;
import com.estudohibernateworkstest.EntityManagerTest;

public class GroupByCriteriaTest extends EntityManagerTest {

	@Test
	public void agruparResultado02() {
		// Total de vendas por catehoria

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
		Root<ItemPedido> root = criteriaQuery.from(ItemPedido.class);
		Join<ItemPedido, Produto> joinProduto = root.join(ItemPedido_.produto);
		Join<Produto, Categoria> joinProdutoCategoria = joinProduto.join(Produto_.categorias);

		criteriaQuery.multiselect(joinProdutoCategoria.get(Categoria_.nome),
				criteriaBuilder.sum(root.get(ItemPedido_.precoProduto)));
		criteriaQuery.groupBy(joinProdutoCategoria.get(Categoria_.id));

		TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);
		List<Object[]> lista = typedQuery.getResultList();

		lista.forEach(arr -> System.out.println("Nome categoria: " + arr[0] + ", Sum: " + arr[1]));
	}

	@Test
	public void agrupar() {
		// Quantidade de produtos por categorias

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
		Root<Categoria> root = criteriaQuery.from(Categoria.class);
		Join<Categoria, Produto> joinProduto = root.join(Categoria_.produtos, JoinType.LEFT);

		criteriaQuery.multiselect(root.get(Categoria_.nome), criteriaBuilder.count(joinProduto.get(Produto_.id)));
		criteriaQuery.groupBy(root.get(Categoria_.id));

		TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);
		List<Object[]> lista = typedQuery.getResultList();

		lista.forEach(arr -> System.out.println("Nome: " + arr[0] + ", Count: " + arr[1]));

	}
}
