package com.estudo.hibernate.works.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Atributo {

	@NotBlank
	@Column(length = 100, nullable = false)
	private String nome;

	@NotBlank
	private String valor;

}
