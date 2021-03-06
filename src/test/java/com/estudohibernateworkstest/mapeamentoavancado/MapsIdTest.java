package com.estudohibernateworkstest.mapeamentoavancado;

import java.time.LocalDateTime;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.estudohibernateworks.model.Cliente;
import com.estudohibernateworks.model.ItemPedido;
import com.estudohibernateworks.model.ItemPedidoId;
import com.estudohibernateworks.model.NotaFiscal;
import com.estudohibernateworks.model.Pedido;
import com.estudohibernateworks.model.Produto;
import com.estudohibernateworks.model.StatusPedido;
import com.estudohibernateworkstest.EntityManagerTest;

public class MapsIdTest extends EntityManagerTest {

	 	@Test
	    public void inserirPagamento() {
	 		entityManager.getTransaction().begin();
	        Pedido pedido = entityManager.find(Pedido.class, 1);

	        NotaFiscal notaFiscal = new NotaFiscal();
	        notaFiscal.setPedido(pedido);
	        notaFiscal.setDataEmissao(new Date());
	        //notaFiscal.setXml(carregarNotaFiscal());

	        entityManager.persist(notaFiscal);
	        entityManager.getTransaction().commit();

	        entityManager.clear();

	        NotaFiscal notaFiscalVarificacao = entityManager.find(NotaFiscal.class, notaFiscal.getId());
	        Assert.assertNotNull(notaFiscalVarificacao);
	        Assert.assertEquals(pedido.getId(), notaFiscalVarificacao.getId());
	    }

	    @Test
	    public void inserirItemPedido() {
	        Cliente cliente = entityManager.find(Cliente.class, 1);
	        Produto produto = entityManager.find(Produto.class, 1);

	        Pedido pedido = new Pedido();
	        pedido.setCliente(cliente);
	        pedido.setDataCriacao(LocalDateTime.now());
	        pedido.setStatus(StatusPedido.AGUARDANDO);
	        pedido.setTotal(produto.getPreco());

	        ItemPedido itemPedido = new ItemPedido();
	        itemPedido.setId(new ItemPedidoId());
	        itemPedido.setPedido(pedido);
	        itemPedido.setProduto(produto);
	        itemPedido.setPrecoProduto(produto.getPreco());
	        itemPedido.setQuantidade(1);

	        entityManager.getTransaction().begin();
	        entityManager.persist(pedido);
	        entityManager.persist(itemPedido);
	        entityManager.getTransaction().commit();

	        entityManager.clear();

	        ItemPedido itemPedidoVerificacao = entityManager.find(
	                ItemPedido.class, new ItemPedidoId(pedido.getId(), produto.getId()));
	        Assert.assertNotNull(itemPedidoVerificacao);
	    }
	}