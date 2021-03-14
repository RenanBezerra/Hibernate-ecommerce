package com.estudohibernateworkstest.iniciandocomjpa;

import org.junit.Assert;
import org.junit.Test;

import com.estudohibernateworks.model.Cliente;
import com.estudohibernateworks.model.Produto;
import com.estudohibernateworks.model.SexoCliente;
import com.estudohibernateworkstest.EntityManagerTest;

public class PrimeiroCrudTest extends EntityManagerTest {

	@Test
	public void inserirRegistro() {
		Cliente cliente = new Cliente();

		//cliente.setId(3);
		cliente.setNome("José Lucas");
		cliente.setCpf("333");
		cliente.setSexo(SexoCliente.MASCULINO);

		entityManager.getTransaction().begin();
		entityManager.persist(cliente);
		entityManager.getTransaction().commit();

		entityManager.clear();

		Cliente clienteVerificacaoCliente = entityManager.find(Cliente.class, cliente.getId());
		Assert.assertNotNull(clienteVerificacaoCliente);

	}

	@Test
	public void buscarPorIdentificador() {
		Produto produto = entityManager.find(Produto.class, 1);

		Assert.assertNotNull(produto);
		Assert.assertEquals("Kindle", produto.getNome());
	}

	@Test
	public void atualizarRegistro() {
		Cliente cliente = new Cliente();

		cliente.setId(1);
		cliente.setNome("Fernando Medeiros Silva");

		entityManager.getTransaction().begin();
		entityManager.merge(cliente);
		entityManager.getTransaction().commit();

		entityManager.clear();

		Cliente clienteVerificacao = entityManager.find(Cliente.class, cliente.getId());
		Assert.assertEquals("Fernando Medeiros Silva", clienteVerificacao.getNome());
	}

	@Test
	public void removerRegistro() {
		Cliente cliente = entityManager.find(Cliente.class, 2);

		entityManager.getTransaction().begin();
		entityManager.remove(cliente);
		entityManager.getTransaction().commit();

		entityManager.clear();

		Cliente clienteVerificacao = entityManager.find(Cliente.class, cliente.getId());
		Assert.assertNull(clienteVerificacao);
	}

}
