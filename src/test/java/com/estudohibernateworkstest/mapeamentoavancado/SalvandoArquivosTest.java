package com.estudohibernateworkstest.mapeamentoavancado;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.estudo.hibernate.works.model.NotaFiscal;
import com.estudo.hibernate.works.model.Pedido;
import com.estudo.hibernate.works.model.Produto;
import com.estudohibernateworkstest.EntityManagerTest;

public class SalvandoArquivosTest extends EntityManagerTest {

	@Test
	public void salvarFotoProduto() {
		entityManager.getTransaction().begin();
		Produto produto = entityManager.find(Produto.class, 1);
		produto.setFoto(carregarFoto());
		entityManager.getTransaction().commit();

		entityManager.clear();

		Produto produtoVerificacao = entityManager.find(Produto.class, 1);
		Assert.assertNotNull(produtoVerificacao.getFoto());
		Assert.assertTrue(produtoVerificacao.getFoto().length > 0);
	}

	@Test
	public void salvarXmlNota() {
		Pedido pedido = entityManager.find(Pedido.class, 1);

		NotaFiscal notaFiscal = new NotaFiscal();
		notaFiscal.setPedido(pedido);
		notaFiscal.setDataEmissao(new Date());
		notaFiscal.setXml(carregarNotaFiscal());

		entityManager.getTransaction().begin();
		entityManager.persist(notaFiscal);
		entityManager.getTransaction().commit();

		entityManager.clear();

		NotaFiscal notaFiscalVerificacao = entityManager.find(NotaFiscal.class, notaFiscal.getId());
		Assert.assertNotNull(notaFiscal.getXml());
		Assert.assertTrue(notaFiscalVerificacao.getXml().length > 0);

//		try {
//			OutputStream out = new FileOutputStream(
//					Files.createFile(Paths.get(System.getProperty("user.home")+"/xml.xml")).toFile());
//			out.write(notaFiscalVerificacao.getXml());
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
	}

	private static byte[] carregarFoto() {
		return carregarArquivo("/Hibernate-ecommerce/resources/kindle.jpg");
	}

	public static byte[] carregarNotaFiscal() {
		return carregarArquivo("/Hibernate-ecommerce/resources/nota-fiscal.xml");
	}

	private static byte[] carregarArquivo(String nome) {
		try {
			Path path = Paths.get(nome);
			byte[] data = Files.readAllBytes(path);
			return data;
			// return SalvandoArquivosTest.class.getResourceAsStream(
			// "/nota-fiscal.xml").readAllBytes(); java 9
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
