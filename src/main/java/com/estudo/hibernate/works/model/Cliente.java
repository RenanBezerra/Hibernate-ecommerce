package com.estudo.hibernate.works.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.OneToMany;
import javax.persistence.ParameterMode;
import javax.persistence.PostLoad;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@NamedStoredProcedureQuery(name = "compraram_acima_media", procedureName = "compraram_acima_media",
		parameters = {
				@StoredProcedureParameter(name = "ano", type = Integer.class, mode = ParameterMode.IN)
		},
		resultClasses = Cliente.class
		)
@SecondaryTable(name = "cliente_detalhe", pkJoinColumns = @PrimaryKeyJoinColumn(name = "cliente_id"),
			foreignKey = @ForeignKey(name = "fk_cliente_detalhe_cliente"))
@Entity
@Table(name = "cliente", uniqueConstraints = {
		@UniqueConstraint(name = "unq_cpf", columnNames = { "cpf" }) }, indexes = {
				@Index(name = "idx_nome", columnList = "nome") })
public class Cliente extends EntidadeBaseInteger {

	@Column(length = 100, nullable = false)
	private String nome;

	@Column(length = 14, nullable = false)
	private String cpf;

	@ElementCollection
	@CollectionTable(name = "cliente_contato", joinColumns = @JoinColumn(name = "cliente_id",nullable = false,
				foreignKey = @ForeignKey(name = "fk_cliente_contato_cliente")))
	@MapKeyColumn(name = "tipo")
	@Column(name = "descricao")
	private Map<String, String> contatos;

	@Transient
	private String primeiroNome;

	@Column(table = "cliente_detalhe", length = 30, nullable = false)
	@Enumerated(EnumType.STRING)
	private SexoCliente sexo;

	@Column(name = "data_nascimento", table = "cliente_detalhe")
	private LocalDate dataNascimento;

	@OneToMany(mappedBy = "cliente")
	private List<Pedido> pedidos;

	@PostLoad
	public void configurarPrimeiroNome() {
		if (nome != null && !nome.isEmpty()) {
			int index = nome.indexOf(" ");
			if (index > -1) {
				primeiroNome = nome.substring(0, index);
			}

		}
	}

}
