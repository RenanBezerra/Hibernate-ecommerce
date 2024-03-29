package com.estudohibernateworkstest.jpql;

import java.util.List;

import javax.persistence.TypedQuery;

import org.junit.Assert;
import org.junit.Test;

import com.estudohibernateworkstest.EntityManagerTest;

public class GroupByTest extends EntityManagerTest {

	@Test
	public void condicionarAgrupamentoComHaving() {
		// Total de vendas dentre as categorias que mais vendem.
		String jpql = "select cat.nome, sum(ip.precoProduto) from ItemPedido ip "
				+ " join ip.produto pro join pro.categorias cat " + " group by cat.id "
				+ " having sum(ip.precoProduto) > 1500";

		TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

		List<Object[]> lista = typedQuery.getResultList();

		Assert.assertFalse(lista.isEmpty());

		lista.forEach(arr -> System.out.println("With having: " + arr[0] + ", " + arr[1]));
	}

	@Test
	public void agruparEFiltrarResultado() {
		// Total de vendas por mes where ano da venda = ano atual
//		String jpql = "select concat(year(p.dataCriacao), '/', function('monthname', p.dataCriacao)),sum(p.total)"
//				+ " from Pedido p "
//				+ " where year(p.dataCriacao) = year(current_date) "
//				+ " group by year(p.dataCriacao), month(p.dataCriacao) ";

		// Total de vendas por categoria where ano da venda = ano atual
//		String jpql = "select c.nome, sum(ip.precoProduto) from ItemPedido ip "
//				+ " join ip.produto pro join pro.categorias c join ip.pedido p "
//				+ " where year(p.dataCriacao) = year(current_date) and month (p.dataCriacao) = month(current_date) "
//				+ " group by c.id";

		// Total de vendas por cliente where ano= anoAtual e mes > mes atual -3 dias
		String jpql = "select c.nome, sum(ip.precoProduto) from ItemPedido ip "
				+ " join ip.pedido p join p.cliente c join ip.pedido p "
				+ " where year(p.dataCriacao) = year(current_date) and month(p.dataCriacao) >= (month(current_date) -3) "
				+ " group by c.id";
		TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

		List<Object[]> lista = typedQuery.getResultList();

		Assert.assertFalse(lista.isEmpty());

		lista.forEach(arr -> System.out.println("groupBy com where: " + arr[0] + "," + arr[1]));
	}

	@Test
	public void agruparResultado() {
		// Quantidade de produtos
//		String jpql = "select c.nome, count(p.id) from Categoria c join c.produtos p group by c.id";

		// Total de vendas por mes
//		String jpql = "select concat(year(p.dataCriacao), ' / ' , function('monthname', p.dataCriacao)), sum(p.total)"
//				+ " from Pedido p group by year(p.dataCriacao), month(p.dataCriacao) ";

		// Total de vendas por categoria
//		String jpql = "select c.nome, sum(ip.precoProduto) from ItemPedido ip "
//				+ " join ip.produto pro join pro.categorias c "
//				+ " group by c.id";

		// Total de vendas por cliente
//		String jpql = "select c.nome , sum(ip.precoProduto) from ItemPedido ip "
//				+ " join ip.pedido pe join pe.cliente c "
//				+ " group by c.id";

		// Total de vendas por dia e por categoria
//		String jpql = "select day(pedi.dataCriacao), sum(ip.precoProduto) from Pedido pedi,"
//				+ " join pedi.itens it "
//		+ " join it.produto pro join pro.categorias c "
//		+ " group by day(pedi.dataCriacao), c.id";

		String jpql = "select " + " concat(year(p.dataCriacao), '/', month(p.dataCriacao), '/', day(p.dataCriacao)), "
				+ " concat(c.nome, ': ', sum(ip.precoProduto)) "
				+ " from ItemPedido ip join ip.pedido p join ip.produto pro join pro.categorias c "
				+ " group by year(p.dataCriacao), month(p.dataCriacao), day(p.dataCriacao), c.id "
				+ " order by p.dataCriacao, c.nome ";

		TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

		List<Object[]> lista = typedQuery.getResultList();

		Assert.assertFalse(lista.isEmpty());
		lista.forEach(arr -> System.out.println("GroupBy: " + arr[0] + ", " + arr[1]));
	}
}
