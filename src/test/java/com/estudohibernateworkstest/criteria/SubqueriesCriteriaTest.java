package com.estudohibernateworkstest.criteria;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.junit.Assert;
import org.junit.Test;

import com.estudo.hibernate.works.model.Categoria;
import com.estudo.hibernate.works.model.Categoria_;
import com.estudo.hibernate.works.model.Cliente;
import com.estudo.hibernate.works.model.ItemPedido;
import com.estudo.hibernate.works.model.ItemPedidoId_;
import com.estudo.hibernate.works.model.ItemPedido_;
import com.estudo.hibernate.works.model.Pedido;
import com.estudo.hibernate.works.model.Pedido_;
import com.estudo.hibernate.works.model.Produto;
import com.estudo.hibernate.works.model.Produto_;
import com.estudohibernateworkstest.EntityManagerTest;

public class SubqueriesCriteriaTest extends EntityManagerTest {

	@Test
	public void pesquisarComAll03() {
		// Todos os produtos que sempre foram vendidos pelo mesmo preco
		//String jpql = "select distinct p from ItemPedido ip join ip.produto p where "+
		//" ip.precoProduto = ALL "+
		//		" (select precoProduto from ItemPedido where produto = p and id <> ip.id)";
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
		Root<ItemPedido> root = criteriaQuery.from(ItemPedido.class);
		
		criteriaQuery.select(root.get(ItemPedido_.produto));
		criteriaQuery.distinct(true);
		
		Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class);
		Root<ItemPedido> subqueryRoot = subquery.from(ItemPedido.class);
		subquery.select(subqueryRoot.get(ItemPedido_.precoProduto));
		subquery.where(
				criteriaBuilder.equal(subqueryRoot.get(ItemPedido_.produto), root.get(ItemPedido_.produto)),
				criteriaBuilder.notEqual(subqueryRoot, root)
				
				);
		criteriaQuery.where(
				criteriaBuilder.equal(root.get(ItemPedido_.precoProduto), criteriaBuilder.all(subquery))
				);
		TypedQuery<Produto> typedQuery = entityManager.createQuery(criteriaQuery);
		
		List<Produto> lista = typedQuery.getResultList();
		Assert.assertFalse(lista.isEmpty());
		
		lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
		
		
	}
	
	@Test
	public void pesquisarComAny02() {
		// Todos os produtos que já foram vendidos por um preco diferente do atual
		//String jpql = "select p from Produto p "+
		//"where p.preco <> ANY (select precoProduto from ItemPedido where produto = p)";
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
		Root<Produto> root = criteriaQuery.from(Produto.class);
		
		criteriaQuery.select(root);
		
		Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class);
		Root<ItemPedido> subqueryRoot = subquery.from(ItemPedido.class);
		subquery.select(subqueryRoot.get(ItemPedido_.precoProduto));
		subquery.where(criteriaBuilder.equal(subqueryRoot.get(ItemPedido_.produto), root));
		
		criteriaQuery.where(
				criteriaBuilder.notEqual(root.get(Produto_.preco), criteriaBuilder.any(subquery))
				);
		
		TypedQuery<Produto> typedQuery = entityManager.createQuery(criteriaQuery);
		
		List<Produto> lista = typedQuery.getResultList();
		Assert.assertFalse(lista.isEmpty());
		
		lista.forEach(obj -> System.out.println("ID: "+ obj.getId()));
		
	
	}
	
	@Test
	public void pesquisarComAny01() {
		// Todos os produtos que já foram vendidos, pelo menos, uma vez pelo preco atual.
		//String jpql = "select p from Produto p "+
		//"where p.preco = ANY (select precoProduto from ItemPedido where produto = p)";
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
		Root<Produto> root = criteriaQuery.from(Produto.class);
		
		criteriaQuery.select(root);
		
		Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class);
		Root<ItemPedido> subqueryRoot = subquery.from(ItemPedido.class);
		subquery.select(subqueryRoot.get(ItemPedido_.precoProduto));
		subquery.where(criteriaBuilder.equal(subqueryRoot.get(ItemPedido_.produto), root));
		
		criteriaQuery.where(
				criteriaBuilder.equal(root.get(Produto_.preco), criteriaBuilder.any(subquery))
				);
		TypedQuery<Produto> typedQuery = entityManager.createQuery(criteriaQuery);
		
		List<Produto> lista = typedQuery.getResultList();
		Assert.assertFalse(lista.isEmpty());
		
		lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
	}
	
	@Test
	public void pesquisarComAll02() {
		// Todos os produtos nao foram vendidos mais depois que encareceram
		// String jpql = "select p from Produto p where "+
		// "p.preco > ALL (select precoProduto from ItemPedido where produto = p)";

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
		Root<Produto> root = criteriaQuery.from(Produto.class);

		criteriaQuery.select(root);

		Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class);
		Root<ItemPedido> subqueryRoot = subquery.from(ItemPedido.class);
		subquery.select(subqueryRoot.get(ItemPedido_.precoProduto));
		subquery.where(criteriaBuilder.equal(subqueryRoot.get(ItemPedido_.produto), root));

		criteriaQuery.where(criteriaBuilder.greaterThan(root.get(Produto_.preco), criteriaBuilder.all(subquery)),
				criteriaBuilder.exists(subquery));

		TypedQuery<Produto> typedQuery = entityManager.createQuery(criteriaQuery);

		List<Produto> lista = typedQuery.getResultList();
		Assert.assertFalse(lista.isEmpty());

		lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
	}

	@Test
	public void pesquisarComAll() {
		// Todos os produtos que SemanticPredicateAdapter foramAdapter vendidos pelo
		// Preco atual.
		// String jpql = "select p from Produto p where "+
		// " p.preco = ALL (select precoProduto from ItemPedido where produto = p");

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
		Root<Produto> root = criteriaQuery.from(Produto.class);

		criteriaQuery.select(root);

		Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class);
		Root<ItemPedido> subqueryRoot = subquery.from(ItemPedido.class);
		subquery.select(subqueryRoot.get(ItemPedido_.precoProduto));
		subquery.where(criteriaBuilder.equal(subqueryRoot.get(ItemPedido_.produto), root));

		criteriaQuery.where(criteriaBuilder.equal(root.get(Produto_.preco), criteriaBuilder.all(subquery)));

		TypedQuery<Produto> typedQuery = entityManager.createQuery(criteriaQuery);

		List<Produto> lista = typedQuery.getResultList();
		Assert.assertFalse(lista.isEmpty());

		lista.forEach(obj -> System.out.println("ID: " + obj.getId()));

	}

	@Test
	public void pesquisarComExistsDiferentes() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
		Root<Produto> root = criteriaQuery.from(Produto.class);

		criteriaQuery.select(root);

		Subquery<Integer> subquery = criteriaQuery.subquery(Integer.class);
		Root<ItemPedido> subqueryRoot = subquery.from(ItemPedido.class);
		subquery.select(criteriaBuilder.literal(1));
		subquery.where(criteriaBuilder.equal(subqueryRoot.get(ItemPedido_.produto), root),
				criteriaBuilder.notEqual(subqueryRoot.get(ItemPedido_.precoProduto), root.get(Produto_.preco)));

		criteriaQuery.where(criteriaBuilder.exists(subquery));

		TypedQuery<Produto> typedQuery = entityManager.createQuery(criteriaQuery);

		List<Produto> lista = typedQuery.getResultList();
		Assert.assertFalse(lista.isEmpty());
	}

	@Test
	public void pesquisarComIn02() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
		Root<Pedido> root = criteriaQuery.from(Pedido.class);

		criteriaQuery.select(root);

		Subquery<Integer> subquery = criteriaQuery.subquery(Integer.class);
		Root<ItemPedido> subqueryRoot = subquery.from(ItemPedido.class);
		Join<ItemPedido, Produto> subqueryJoinProduto = subqueryRoot.join(ItemPedido_.produto);
		Join<Produto, Categoria> subqueryJoinProdutoCategoria = subqueryJoinProduto.join(Produto_.categorias);

		subquery.select(subqueryRoot.get(ItemPedido_.id).get(ItemPedidoId_.pedidoId));
		subquery.where(criteriaBuilder.equal(subqueryJoinProdutoCategoria.get(Categoria_.id), 2));

		criteriaQuery.where(root.get(Pedido_.id).in(subquery));

		TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);

		List<Pedido> lista = typedQuery.getResultList();
		Assert.assertFalse(lista.isEmpty());

		lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
	}

	@Test
	public void pesquisarComSubquery() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Cliente> criteriaQuery = criteriaBuilder.createQuery(Cliente.class);
		Root<Cliente> root = criteriaQuery.from(Cliente.class);

		criteriaQuery.select(root);

		Subquery<Long> subquery = criteriaQuery.subquery(Long.class);
		Root<Pedido> subqueryRoot = subquery.from(Pedido.class);
		subquery.select(criteriaBuilder.count(criteriaBuilder.literal(1)));
		subquery.where(criteriaBuilder.equal(subqueryRoot.get(Pedido_.cliente), root));

		criteriaQuery.where(criteriaBuilder.greaterThan(subquery, 2L));

		TypedQuery<Cliente> typedQuery = entityManager.createQuery(criteriaQuery);

		List<Cliente> lista = typedQuery.getResultList();
		Assert.assertFalse(lista.isEmpty());

		lista.forEach(obj -> System.out.println("ID: " + obj.getId() + ", Nome: " + obj.getNome()));
	}

	@Test
	public void pesquisarComExists() {
		// Todos os produtos que já foram vendidos.
//		String jpql = "select p from Produto p where exists "
		// + " (select 1 from ItemPedido ip2 join ip2.produto p2 where p2 = p)";

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
		Root<Produto> root = criteriaQuery.from(Produto.class);

		criteriaQuery.select(root);

		Subquery<Integer> subquery = criteriaQuery.subquery(Integer.class);
		Root<ItemPedido> subqueryRoot = subquery.from(ItemPedido.class);
		subquery.select(criteriaBuilder.literal(1));
		subquery.where(criteriaBuilder.equal(subqueryRoot.get(ItemPedido_.produto), root));

		criteriaQuery.where(criteriaBuilder.exists(subquery));

		TypedQuery<Produto> typedQuery = entityManager.createQuery(criteriaQuery);

		List<Produto> lista = typedQuery.getResultList();
		Assert.assertFalse(lista.isEmpty());

		lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
	}

	@Test
	public void pesquisarComIn() {
//		String jpql = "select p from Pedido p where p.id in "+
		// " (select p2.id from ItemPedido i2 " +
		// " join i2.pedido p2 join i2.produto pro2 where pro2.preco > 100)";

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
		Root<Pedido> root = criteriaQuery.from(Pedido.class);

		criteriaQuery.select(root);

		Subquery<Integer> subquery = criteriaQuery.subquery(Integer.class);
		Root<ItemPedido> subqueryRoot = subquery.from(ItemPedido.class);
		Join<ItemPedido, Pedido> subqueryJoinPedido = subqueryRoot.join(ItemPedido_.pedido);
		Join<ItemPedido, Produto> subqueryJoinProduto = subqueryRoot.join(ItemPedido_.produto);
		subquery.select(subqueryJoinPedido.get(Pedido_.id));
		subquery.where(criteriaBuilder.greaterThan(subqueryJoinProduto.get(Produto_.preco), new BigDecimal(100)));

		criteriaQuery.where(root.get(Pedido_.id).in(subquery));

		TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);

		List<Pedido> lista = typedQuery.getResultList();
		Assert.assertFalse(lista.isEmpty());

		lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
	}

	@Test
	public void pesquisar03() {
		// Bons clientes.
		// String jpql = "select from Cliente c where "+
//		" 500 < (select sum(p.total) from Pedido p where p.cliente = c)";

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Cliente> criteriaQuery = criteriaBuilder.createQuery(Cliente.class);
		Root<Cliente> root = criteriaQuery.from(Cliente.class);

		criteriaQuery.select(root);

		Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class);
		Root<Pedido> subqueryRoot = subquery.from(Pedido.class);
		subquery.select(criteriaBuilder.sum(subqueryRoot.get(Pedido_.total)));
		subquery.where(criteriaBuilder.equal(root, subqueryRoot.get(Pedido_.cliente)));

		criteriaQuery.where(criteriaBuilder.greaterThan(subquery, new BigDecimal(1300)));

		TypedQuery<Cliente> typedQuery = entityManager.createQuery(criteriaQuery);
		List<Cliente> lista = typedQuery.getResultList();
		Assert.assertFalse(lista.isEmpty());

		lista.forEach(obj -> System.out.println("ID: " + obj.getId() + ", Nome: " + obj.getNome()));

	}

	@Test
	public void pesquisarSubqueries02() {
		// Todos os pedidos acima da média de vendas
//		String jpql = "select p from Pedido p where "+
//		"p.total > (select avg(total) from Pedido)";

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
		Root<Pedido> root = criteriaQuery.from(Pedido.class);

		criteriaQuery.select(root);

		Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class);
		Root<Pedido> subqueryRoot = subquery.from(Pedido.class);
		subquery.select(criteriaBuilder.avg(subqueryRoot.get(Pedido_.total)).as(BigDecimal.class));

		criteriaQuery.where(criteriaBuilder.greaterThan(root.get(Pedido_.total), subquery));

		TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);

		List<Pedido> lista = typedQuery.getResultList();
		Assert.assertFalse(lista.isEmpty());

		lista.forEach(obj -> System.out.println("ID: " + obj.getId() + ", Total: " + obj.getTotal()));

	}

	@Test
	public void pesquisarSubqeries01() {
		// o produto ou os produtos mais caros da base

//		String jpql = "select p from Produto p where "+
//		"p.preco = (select max(preco) from Produto)";

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
		Root<Produto> root = criteriaQuery.from(Produto.class);

		criteriaQuery.select(root);

		Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class);
		Root<Produto> subqueryRoot = subquery.from(Produto.class);
		subquery.select(criteriaBuilder.max(subqueryRoot.get(Produto_.preco)));

		criteriaQuery.where(criteriaBuilder.equal(root.get(Produto_.preco), subquery));

		TypedQuery<Produto> typedQuery = entityManager.createQuery(criteriaQuery);

		List<Produto> lista = typedQuery.getResultList();
		Assert.assertFalse(lista.isEmpty());

		lista.forEach(obj -> System.out
				.println("ID: " + obj.getId() + ", Nome: " + obj.getNome() + ", Preco: " + obj.getPreco()));

	}
}
