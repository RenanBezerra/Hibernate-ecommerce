package com.estudohibernateworks.model;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@DiscriminatorValue("boleto")
@Entity
public class PagamentoBoleto extends Pagamento {

	@Column(name = "codigo_barras")
	private String codigoBarras;

}
