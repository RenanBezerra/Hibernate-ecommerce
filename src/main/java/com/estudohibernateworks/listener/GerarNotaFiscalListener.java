package com.estudohibernateworks.listener;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.estudohibernateworks.model.Pedido;
import com.estudohibernateworks.service.NotaFiscalService;

public class GerarNotaFiscalListener {

	private NotaFiscalService notaFiscalService = new NotaFiscalService();
	
	@PrePersist
	@PreUpdate
	public void gerar(Pedido pedido) {
		if (pedido.isPago() && pedido.getNotaFiscal() == null) {
			notaFiscalService.gerar(pedido);
		}
		
	}
}
