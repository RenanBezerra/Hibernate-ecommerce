package com.estudohibernateworkstest.operacoesemcascata;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import com.estudo.hibernate.works.model.Categoria;
import com.estudo.hibernate.works.model.Cliente;
import com.estudo.hibernate.works.model.ItemPedido;
import com.estudo.hibernate.works.model.ItemPedidoId;
import com.estudo.hibernate.works.model.Pedido;
import com.estudo.hibernate.works.model.Produto;
import com.estudo.hibernate.works.model.StatusPedido;
import com.estudohibernateworkstest.EntityManagerTest;

public class CascadeTypeMergeTest extends EntityManagerTest{

	@Test
	public void atualizarProdutoComCategoria() {
		Produto produto = new Produto();
		produto.setId(1);
		produto.setDataUltimaAtualizacao(LocalDateTime.now());
		produto.setPreco(new BigDecimal(500));
		produto.setNome("Kindle");
		produto.setDescricao("Agora com iluminacao embutida ajustavel.");
		
		Categoria categoria = new Categoria();
		categoria.setId(2);
		categoria.setNome("Tablets");
		
		produto.setCategorias(Arrays.asList(categoria)); //cascade.merge
		
		entityManager.getTransaction().begin();
		entityManager.merge(produto);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Categoria categoriaVerificacao = entityManager.find(Categoria.class, categoria.getId());
		Assert.assertEquals("Tablets", categoriaVerificacao.getNome());
		
	}
	//@Test
	public void atualizaPedidoComItens() {
		Cliente cliente = entityManager.find(Cliente.class, 1);
		Produto produto = entityManager.find(Produto.class, 1);
		
		Pedido pedido = new Pedido();
		pedido.setId(1);
		pedido.setCliente(cliente);
		pedido.setStatus(StatusPedido.AGUARDANDO);
		
		ItemPedido itemPedido = new ItemPedido();
		itemPedido.setId(new ItemPedidoId());
		itemPedido.getId().setPedidoId(pedido.getId());
		itemPedido.getId().setProdutoId(produto.getId());
		itemPedido.setPedido(pedido);
		itemPedido.setProduto(produto);
		itemPedido.setQuantidade(3);
		itemPedido.setPrecoProduto(produto.getPreco());
		
		pedido.setItens(Arrays.asList(itemPedido));//cascadeType.merge
		
		entityManager.getTransaction().begin();
		entityManager.merge(pedido);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		ItemPedido itemPedidoVerificacao = entityManager.find(ItemPedido.class, itemPedido.getId());
		Assert.assertTrue(itemPedidoVerificacao.getQuantidade().equals(3));
	}

	//@Test
	public void atualizarItemPedidoComPedido() {
		Cliente cliente = entityManager.find(Cliente.class, 1);
		Produto produto = entityManager.find(Produto.class, 1);
		
		Pedido pedido = new Pedido();
		pedido.setId(1);
		pedido.setCliente(cliente);
		pedido.setStatus(StatusPedido.PAGO);
		
		ItemPedido itemPedido = new ItemPedido();
		itemPedido.setId(new ItemPedidoId());
		itemPedido.getId().setPedidoId(pedido.getId());
		itemPedido.getId().setProdutoId(produto.getId());
		itemPedido.setPedido(pedido); //cascadeType.Merge
		itemPedido.setProduto(produto);
		itemPedido.setQuantidade(5);
		itemPedido.setPrecoProduto(produto.getPreco());
		
		pedido.setItens(Arrays.asList(itemPedido));
		
		entityManager.getTransaction().begin();
		entityManager.merge(itemPedido);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		ItemPedido itemPedidoVerificacao = entityManager.find(ItemPedido.class, itemPedido.getId());
		Assert.assertTrue(StatusPedido.PAGO.equals(itemPedidoVerificacao.getPedido().getStatus()));
		
	}
}
