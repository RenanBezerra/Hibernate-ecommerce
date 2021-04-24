package com.estudohibernateworkstest.criteria;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.junit.Assert;
import org.junit.Test;

import com.estudo.hibernate.works.model.Cliente;
import com.estudo.hibernate.works.model.Cliente_;
import com.estudohibernateworkstest.EntityManagerTest;

public class FuncoesCriteriaTest extends EntityManagerTest {

	@Test
	public void aplicarFuncaoString() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
		Root<Cliente> root = criteriaQuery.from(Cliente.class);
		
		criteriaQuery.multiselect(
				root.get(Cliente_.nome),
				criteriaBuilder.concat("Nome do cliente: ", root.get(Cliente_.nome)),
				criteriaBuilder.length(root.get(Cliente_.nome)),
				criteriaBuilder.locate(root.get(Cliente_.nome), "a"),
				criteriaBuilder.substring(root.get(Cliente_.nome), 1,2),
				criteriaBuilder.lower(root.get(Cliente_.nome)),
				criteriaBuilder.upper(root.get(Cliente_.nome)),
				criteriaBuilder.trim(root.get(Cliente_.nome))
				);
		
		criteriaQuery.where(criteriaBuilder.equal(
				criteriaBuilder.substring(root.get(Cliente_.nome), 1, 1), "M"));
		
		TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);
		
		List<Object[]> lista = typedQuery.getResultList();
		Assert.assertFalse(lista.isEmpty());
		
		lista.forEach(arr -> System.out.println(
				arr[0]
						+ ",\n concat: " + arr[1]
								+ ",\n length: " + arr[2]
										+ ",\n locate: " + arr[3]
												+ ",\n substring: " + arr[4]
														+ ",\n lower: " + arr[5]
																+ ",\n upper: " + arr[6]
																		+ ",\n trim: " + arr[7]
				));
	}

}
