package com.estudohibernateworkstest.mapeamentobasico;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.Test;

import com.estudo.hibernate.works.model.Cliente;
import com.estudo.hibernate.works.model.EnderecoEntregaPedido;
import com.estudo.hibernate.works.model.Pedido;
import com.estudo.hibernate.works.model.StatusPedido;
import com.estudohibernateworkstest.EntityManagerTest;

public class MapeamentoObjetoEmbutidoTest extends EntityManagerTest {

	@Test
	public void analisarMapeamentoObjetoEmbutido() {
		Cliente cliente = entityManager.find(Cliente.class, 1);

		EnderecoEntregaPedido endereco = new EnderecoEntregaPedido();
		endereco.setCep("00000-00");
		endereco.setLogradouro("Rua das Laranjeiras");
		endereco.setNumero("123");
		endereco.setBairro("Centro");
		endereco.setCidade("Uberlandia");
		endereco.setEstado("MG");

		Pedido pedido = new Pedido();
		// pedido.setId(1);
		pedido.setDataCriacao(LocalDateTime.now());
		pedido.setStatus(StatusPedido.AGUARDANDO);
		pedido.setTotal(new BigDecimal(1000));
		pedido.setEnderecoEntrega(endereco);
		pedido.setCliente(cliente);

		entityManager.getTransaction().begin();
		entityManager.persist(pedido);
		entityManager.getTransaction().commit();

		entityManager.clear();

		Pedido pedidoVerificacao = entityManager.find(Pedido.class, pedido.getId());

		Assert.assertNotNull(pedidoVerificacao);
		Assert.assertNotNull(pedidoVerificacao.getEnderecoEntrega());
		Assert.assertNotNull(pedidoVerificacao.getEnderecoEntrega().getCep());

	}

}
