package com.estudohibernateworks.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "pagamento_boleto")
public class PagamentoBoleto extends Pagamento {

	@Column(name = "codigo_barras")
	private String codigoBarras;

}
