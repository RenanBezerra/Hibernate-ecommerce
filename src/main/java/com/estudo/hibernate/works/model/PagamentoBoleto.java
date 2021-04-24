package com.estudo.hibernate.works.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@DiscriminatorValue("boleto")
@Entity
public class PagamentoBoleto extends Pagamento {

	@Column(name = "codigo_barras", length = 100)
	private String codigoBarras;
	
	@Column(name = "data_vencimento")
	private LocalDate dataVencimento;

}
