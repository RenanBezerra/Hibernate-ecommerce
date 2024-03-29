package com.estudohibernateworkstest.mapeamentoavancado;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.estudo.hibernate.works.model.Cliente;
import com.estudo.hibernate.works.model.Pagamento;
import com.estudo.hibernate.works.model.PagamentoCartao;
import com.estudo.hibernate.works.model.Pedido;
import com.estudo.hibernate.works.model.SexoCliente;
import com.estudo.hibernate.works.model.StatusPagamento;
import com.estudohibernateworkstest.EntityManagerTest;

public class HerancaTest extends EntityManagerTest {

	@Test
	public void salvarCliente() {
		Cliente cliente = new Cliente();
		cliente.setNome("Fernanda Morais");
		cliente.setSexo(SexoCliente.FEMININO);
		cliente.setCpf("333");

		entityManager.getTransaction().begin();
		entityManager.persist(cliente);
		entityManager.getTransaction().commit();

		entityManager.clear();

		Cliente clienteVerificacao = entityManager.find(Cliente.class, cliente.getId());
		Assert.assertNotNull(clienteVerificacao.getId());

	}

	@Test
	public void incluirPagamentoPedido() {
		Pedido pedido = entityManager.find(Pedido.class, 1);

		PagamentoCartao pagamentoCartao = new PagamentoCartao();
		pagamentoCartao.setPedido(pedido);
		pagamentoCartao.setStatus(StatusPagamento.PROCESSANDO);
		pagamentoCartao.setNumeroCartao("123");

		entityManager.getTransaction().begin();
		entityManager.persist(pagamentoCartao);
		entityManager.getTransaction().commit();

		entityManager.clear();

		Pedido pedidoVerificacao = entityManager.find(Pedido.class, pedido.getId());
		Assert.assertNotNull(pedidoVerificacao.getPagamento());
	}

	@Test
	public void buscarPagamento() {
		List<Pagamento> pagamentos = entityManager.createQuery("select p from Pagamento p").getResultList();

		Assert.assertFalse(pagamentos.isEmpty());
	}
}
