package com.estudohibernateworkstest.criteria;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import org.junit.Test;

import com.estudo.hibernate.works.model.Categoria;
import com.estudo.hibernate.works.model.Categoria_;
import com.estudo.hibernate.works.model.Cliente;
import com.estudo.hibernate.works.model.Cliente_;
import com.estudo.hibernate.works.model.ItemPedido;
import com.estudo.hibernate.works.model.ItemPedido_;
import com.estudo.hibernate.works.model.Pedido;
import com.estudo.hibernate.works.model.Pedido_;
import com.estudo.hibernate.works.model.Produto;
import com.estudo.hibernate.works.model.Produto_;
import com.estudohibernateworkstest.EntityManagerTest;

public class GroupByCriteriaTest extends EntityManagerTest {

	@Test
	public void condicionarAgrupamentoComHaving() {
		// total de vendas dentre as categorias que mais vendem
		// String jpql = "select cat.nome, sum(ip.precoProduto) from ItemPedido ip " +
		// " join ip.produto pro join pro.categorias cat " +
		// " group by cat.id " +
		// " having sum(ip.precoProduto) > 100 ";

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
		Root<ItemPedido> root = criteriaQuery.from(ItemPedido.class);
		Join<ItemPedido, Produto> joinProduto = root.join(ItemPedido_.produto);
		Join<Produto, Categoria> joinProdutoCategoria = joinProduto.join(Produto_.categorias);

		criteriaQuery.multiselect(joinProdutoCategoria.get(Categoria_.nome),
				criteriaBuilder.sum(root.get(ItemPedido_.precoProduto)),
				criteriaBuilder.avg(root.get(ItemPedido_.precoProduto)));

		criteriaQuery.groupBy(joinProdutoCategoria.get(Categoria_.id));

		criteriaQuery.having(criteriaBuilder.greaterThan(
				criteriaBuilder.avg(root.get(ItemPedido_.precoProduto)).as(BigDecimal.class), new BigDecimal(700)));

		TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);
		List<Object[]> lista = typedQuery.getResultList();

		lista.forEach(
				arr -> System.out.println("Nome categorias: " + arr[0] + ", SUM: " + arr[1] + ", AVG: " + arr[2]));
	}

	@Test
	public void agruparResultadoComFuncoes() {
		// Total de vendas por mes
		// String jpql = "select concat(year(p.dataCriacao), '/', function('monthname',
		// p.dataCriacao)), sum(p.total) " +
		// " from Pedido p " +
		// " group by yeap.dataCriacao), month(p.dataCriacao) ";

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
		Root<Pedido> root = criteriaQuery.from(Pedido.class);

		Expression<Integer> anoCriacaoPedido = criteriaBuilder.function("year", Integer.class,
				root.get(Pedido_.dataCriacao));
		Expression<Integer> mesCriacaoPedido = criteriaBuilder.function("month", Integer.class,
				root.get(Pedido_.dataCriacao));
		Expression<String> nomeMesCriacaoPedido = criteriaBuilder.function("monthname", String.class,
				root.get(Pedido_.dataCriacao));

		Expression<String> anoMesConcat = criteriaBuilder
				.concat(criteriaBuilder.concat(anoCriacaoPedido.as(String.class), "/"), nomeMesCriacaoPedido);

		criteriaQuery.multiselect(anoMesConcat, criteriaBuilder.sum(root.get(Pedido_.total)));

		criteriaQuery.groupBy(anoCriacaoPedido, mesCriacaoPedido);

		TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);
		List<Object[]> lista = typedQuery.getResultList();

		lista.forEach(arr -> System.out.println("Ano/Mes: " + arr[0] + ", Sum: " + arr[1]));

	}

	@Test
	public void agruparResultado03() {
		// Total de vendas por cliente
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
		Root<ItemPedido> root = criteriaQuery.from(ItemPedido.class);
		Join<ItemPedido, Pedido> joinPedido = root.join(ItemPedido_.pedido);
		Join<Pedido, Cliente> joinPedidoCliente = joinPedido.join(Pedido_.cliente);

		criteriaQuery.multiselect(joinPedidoCliente.get(Cliente_.nome),
				criteriaBuilder.sum(root.get(ItemPedido_.precoProduto)));
		criteriaQuery.groupBy(joinPedidoCliente.get(Cliente_.id));

		TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);
		List<Object[]> lista = typedQuery.getResultList();

		lista.forEach(arr -> System.out.println("Nome do cliente: " + arr[0] + ", Sum: " + arr[1]));
	}

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
