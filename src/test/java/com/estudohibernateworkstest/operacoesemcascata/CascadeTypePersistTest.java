package com.estudohibernateworkstest.operacoesemcascata;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import com.estudo.hibernate.works.model.Cliente;
import com.estudo.hibernate.works.model.ItemPedido;
import com.estudo.hibernate.works.model.ItemPedidoId;
import com.estudo.hibernate.works.model.Pedido;
import com.estudo.hibernate.works.model.Produto;
import com.estudo.hibernate.works.model.SexoCliente;
import com.estudo.hibernate.works.model.StatusPedido;
import com.estudohibernateworkstest.EntityManagerTest;

public class CascadeTypePersistTest extends EntityManagerTest {

	//@Test
	public void persistirPedidoComItens() {
		Cliente cliente = entityManager.find(Cliente.class, 1);
		Produto produto = entityManager.find(Produto.class, 1);

		Pedido pedido = new Pedido();
		pedido.setDataCriacao(LocalDateTime.now());
		pedido.setCliente(cliente);
		pedido.setTotal(produto.getPreco());
		pedido.setStatus(StatusPedido.AGUARDANDO);

		ItemPedido itemPedido = new ItemPedido();
		itemPedido.setId(new ItemPedidoId());
		itemPedido.setPedido(pedido);
		itemPedido.setProduto(produto);
		itemPedido.setQuantidade(1);
		itemPedido.setPrecoProduto(produto.getPreco());

		pedido.setItens(Arrays.asList(itemPedido)); // cascadetype persist

		entityManager.getTransaction().begin();
		entityManager.persist(pedido);
		entityManager.getTransaction().commit();

		entityManager.clear();

		Pedido pedidoVerificacao = entityManager.find(Pedido.class, pedido.getId());
		Assert.assertNotNull(pedido);
		Assert.assertFalse(pedido.getItens().isEmpty());
	}

	@Test
	public void persistirItemPedidoComPedido() {
		Cliente cliente = entityManager.find(Cliente.class, 1);
		Produto produto = entityManager.find(Produto.class, 1);

		Pedido pedido = new Pedido();
		pedido.setDataCriacao(LocalDateTime.now());
		pedido.setCliente(cliente);
		pedido.setTotal(produto.getPreco());
		pedido.setStatus(StatusPedido.AGUARDANDO);

		ItemPedido itemPedido = new ItemPedido();
		itemPedido.setId(new ItemPedidoId());
		itemPedido.setPedido(pedido);// não e necessario cascadetype porque possui @mapsId
		itemPedido.setProduto(produto);
		itemPedido.setQuantidade(1);
		itemPedido.setPrecoProduto(produto.getPreco());

		entityManager.getTransaction().begin();
		entityManager.persist(itemPedido);
		entityManager.getTransaction().commit();

		entityManager.clear();

		Pedido pedidoVerificacao = entityManager.find(Pedido.class, pedido.getId());
		Assert.assertNotNull(pedido);
	}

	//@Test
	public void persistirPedidoComClientes() {
		Cliente cliente = entityManager.find(Cliente.class, 1);
		cliente.setDataNascimento(LocalDate.of(1980, 1, 1));
		cliente.setSexo(SexoCliente.MASCULINO);
		cliente.setNome("Jose Carlos");
		cliente.setCpf("01234567890");

		Pedido pedido = new Pedido();
		pedido.setDataCriacao(LocalDateTime.now());
		pedido.setCliente(cliente);//cascade type persist
		pedido.setTotal(BigDecimal.ZERO);
		pedido.setStatus(StatusPedido.AGUARDANDO);

		entityManager.getTransaction().begin();
		entityManager.persist(pedido);
		entityManager.getTransaction().commit();

		entityManager.clear();

		Cliente clienteVerificacao = entityManager.find(Cliente.class, cliente.getId());
		Assert.assertNotNull(cliente);
	}
}
