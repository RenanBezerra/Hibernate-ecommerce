package com.estudohibernateworkstest.relacionamentos;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.estudohibernateworks.model.NotaFiscal;
import com.estudohibernateworks.model.PagamentoCartao;
import com.estudohibernateworks.model.Pedido;
import com.estudohibernateworks.model.StatusPagamento;
import com.estudohibernateworkstest.EntityManagerTest;

public class RelacionamentoOneToOneTest extends EntityManagerTest {

	@Test
	public void verificarRelacionamento() {

		Pedido pedido = entityManager.find(Pedido.class, 1);

		PagamentoCartao pagamentoCartao = new PagamentoCartao();
		pagamentoCartao.setNumero("1234");
		pagamentoCartao.setStatus(StatusPagamento.PROCESSANDO);
		pagamentoCartao.setPedido(pedido);

		entityManager.getTransaction().begin();
		entityManager.persist(pagamentoCartao);
		entityManager.getTransaction().commit();

		entityManager.clear();

		Pedido pedidoVerificacao = entityManager.find(Pedido.class, pedido.getId());
		Assert.assertNotNull(pedidoVerificacao.getPagamento());

	}
	
	@Test
	public void verificarRelacionamentoPedidoNotaFiscal() {

		Pedido pedido = entityManager.find(Pedido.class, 1);

		NotaFiscal notaFiscal = new NotaFiscal ();
		notaFiscal.setXml("TESTE");
		notaFiscal.setDataEmissao(new Date());
		notaFiscal.setPedido(pedido);

		entityManager.getTransaction().begin();
		entityManager.persist(notaFiscal);
		entityManager.getTransaction().commit();

		entityManager.clear();

		Pedido pedidoVerificacao = entityManager.find(Pedido.class, pedido.getId());
		Assert.assertNotNull(pedidoVerificacao.getNotaFiscal());

	}
	
	
}
