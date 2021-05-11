package com.estudohibernateworkstest.consultasnativas;

import java.util.List;

import javax.persistence.Query;

import org.junit.Test;

import com.estudohibernateworkstest.EntityManagerTest;

public class ConsultaNativaTest extends EntityManagerTest {

	@Test
	public void executeSQL() {
		String sql = "select id, nome from produto";
		Query query = entityManager.createNativeQuery(sql);

		List<Object[]> lista = query.getResultList();

		lista.stream().forEach(arr -> System.out.println(String.format("Produto => ID: %s, Nome: %s", arr[0], arr[1])));

	}
}
