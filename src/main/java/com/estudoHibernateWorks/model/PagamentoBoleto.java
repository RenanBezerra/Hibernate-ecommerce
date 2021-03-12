package com.estudohibernateworks.model;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@DiscriminatorValue("boleto")
@Entity
public class PagamentoBoleto extends Pagamento {

	@Column(name = "codigo_barras", length = 100, nullable = false)
	private String codigoBarras;

}
