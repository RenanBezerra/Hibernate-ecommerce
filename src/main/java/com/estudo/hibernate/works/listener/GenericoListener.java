package com.estudo.hibernate.works.listener;

import javax.persistence.PostLoad;

public class GenericoListener {

	@PostLoad
	public void logCarregamento(Object obj) {
		System.out.println("Entidade" + obj.getClass().getSimpleName() + " foi carregada.");
	}
}
