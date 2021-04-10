package com.estudohibernateworkstest.jpql;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import com.estudohibernateworkstest.EntityManagerTest;

public class OperacoesEmLoteTest extends EntityManagerTest{

	@Test
	public void inserirEmLote() throws IOException {
		InputStream in = OperacoesEmLoteTest.class.getClassLoader()
				.getResourceAsStream("produtos/importar.txt");
	}
}
