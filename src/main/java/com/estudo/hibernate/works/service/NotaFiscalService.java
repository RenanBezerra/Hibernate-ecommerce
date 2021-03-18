package com.estudo.hibernate.works.service;

import com.estudo.hibernate.works.model.Pedido;

public class NotaFiscalService {

	public void gerar(Pedido pedido) {
		System.out.println("Gerando nota para o pedido " + pedido.getId() + ".");
	}
}
