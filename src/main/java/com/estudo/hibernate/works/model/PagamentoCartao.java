package com.estudo.hibernate.works.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@DiscriminatorValue("cartao")
@Entity
public class PagamentoCartao extends Pagamento {

	@NotEmpty
	@Column(name = "numero_cartao",length = 50)
	private String numeroCartao;

}
