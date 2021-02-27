package com.estudohibernateworks.service;

import com.estudohibernateworks.model.Pedido;

public class NotaFiscalService {
	
	

	public void gerar(Pedido pedido) {
		System.out.println( "Gerando nota para o pedido " + pedido.getId() + ".");
	}
}
