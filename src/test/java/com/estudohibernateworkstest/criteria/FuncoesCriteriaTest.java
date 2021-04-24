package com.estudohibernateworkstest.criteria;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import org.junit.Assert;
import org.junit.Test;

import com.estudo.hibernate.works.model.Cliente;
import com.estudo.hibernate.works.model.Cliente_;
import com.estudo.hibernate.works.model.Pagamento;
import com.estudo.hibernate.works.model.PagamentoBoleto;
import com.estudo.hibernate.works.model.PagamentoBoleto_;
import com.estudo.hibernate.works.model.Pedido;
import com.estudo.hibernate.works.model.Pedido_;
import com.estudo.hibernate.works.model.StatusPedido;
import com.estudohibernateworkstest.EntityManagerTest;

public class FuncoesCriteriaTest extends EntityManagerTest {

	@Test
	public void aplicarFuncaoData() {
		//current_date, current_time, current_timestamp
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
		Root<Pedido> root = criteriaQuery.from(Pedido.class);
		Join<Pedido, Pagamento> joinPagamento = root.join(Pedido_.pagamento);
		Join<Pedido, PagamentoBoleto> joinPagamentoBoleto = criteriaBuilder
				.treat(joinPagamento, PagamentoBoleto.class);
		
		criteriaQuery.multiselect(
				root.get(Pedido_.id),
				criteriaBuilder.currentDate(),
				criteriaBuilder.currentTime(),
				criteriaBuilder.currentTimestamp()
				);
		
		criteriaQuery.where(
		criteriaBuilder.between(criteriaBuilder.currentDate(),
				root.get(Pedido_.dataCriacao).as(java.sql.Date.class),
				joinPagamentoBoleto.get(PagamentoBoleto_.dataVencimento).as(java.sql.Date.class)),
			criteriaBuilder.equal(root.get(Pedido_.status), StatusPedido.AGUARDANDO)
			);
		
		TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);
		
		List<Object[]> lista = typedQuery.getResultList();
		Assert.assertFalse(lista.isEmpty());
		
		lista.forEach(arr -> System.out.println(
				arr[0]
						+ ", current_date: " + arr[1]
								+ ", current_time: " + arr[2]
										+", current_timestamp: " + arr[3]
				));
	}
	
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
