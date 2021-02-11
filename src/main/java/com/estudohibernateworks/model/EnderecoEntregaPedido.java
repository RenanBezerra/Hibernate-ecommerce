package com.estudohibernateworks.model;

import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class EnderecoEntregaPedido {

	private String cep;
	
	private String logradouro;
	
	private String numero;
	
	private String complemento;
	
	private String bairro;
	
	private String cidade;
	
	private String estado;
}
