package com.estudohibernateworks.model;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@DiscriminatorValue("cartao")
@Entity
public class PagamentoCartao extends Pagamento {

	@Column(name = "numero_cartao")
	private String numeroCartao;

}
