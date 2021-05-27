package com.estudo.hibernate.works.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.engine.spi.PersistentAttributeInterceptable;
import org.hibernate.engine.spi.PersistentAttributeInterceptor;

import com.estudo.hibernate.works.listener.GenericoListener;
import com.estudo.hibernate.works.listener.GerarNotaFiscalListener;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@NamedEntityGraphs({
	@NamedEntityGraph(
			name = "Pedido.dadosEssenciais",
			attributeNodes = {
					@NamedAttributeNode("dataCriacao"),
					@NamedAttributeNode("status"),
					@NamedAttributeNode("total"),
					@NamedAttributeNode(
							value = "cliente",
							subgraph = "cli"
							)
			},
			subgraphs = {
					@NamedSubgraph(
							name = "cli",
							attributeNodes = {
									@NamedAttributeNode("nome"),
									@NamedAttributeNode("cpf")
							}
							)
			}
			)
})
@EntityListeners({ GerarNotaFiscalListener.class, GenericoListener.class })
@Entity
@Table(name = "pedido")
public class Pedido extends EntidadeBaseInteger implements PersistentAttributeInterceptable {

	@NotNull
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "cliente_id", nullable = false, foreignKey = @ForeignKey(name = "fk_pedido_cliente"))
	private Cliente cliente;

	@NotEmpty
	@OneToMany(mappedBy = "pedido")
	private List<ItemPedido> itens;

	@PastOrPresent
	@Column(name = "data_criacao", updatable = false, nullable = false)
	private LocalDateTime dataCriacao;

	@PastOrPresent
	@Column(name = "data_ultima_atualizacao", insertable = false)
	private LocalDateTime dataUltimaAtualizacao;

	@PastOrPresent
	@Column(name = "data_conclusao")
	private LocalDateTime dataConclusao;

	
	@LazyToOne(LazyToOneOption.NO_PROXY)
	@OneToOne(mappedBy = "pedido", fetch = FetchType.LAZY)
	private NotaFiscal notaFiscal;

	@NotNull
	@Positive
	@Column(precision = 19, scale = 2, nullable = false)
	private BigDecimal total;

	@NotNull
	@Column(length = 30, nullable = false)
	@Enumerated(EnumType.STRING)
	private StatusPedido status;

	@LazyToOne(LazyToOneOption.NO_PROXY)
	@OneToOne(mappedBy = "pedido", fetch = FetchType.LAZY)
	private Pagamento pagamento;

	@Embedded
	private EnderecoEntregaPedido enderecoEntrega;

	public NotaFiscal getNotaFiscal() {
		if (this.persistentAttributeInterceptor != null) {
			return (NotaFiscal) persistentAttributeInterceptor.readObject(this, "notaFiscal", this.notaFiscal);
		}

		return this.notaFiscal;
	}

	public void setNotaFiscal(NotaFiscal notaFiscal) {
		if (this.persistentAttributeInterceptor != null) {
			this.notaFiscal = (NotaFiscal) persistentAttributeInterceptor.writeObject(this, "notaFiscal",
					this.notaFiscal, notaFiscal);

		} else {
			this.notaFiscal = notaFiscal;
		}
	}

	public Pagamento getPagamento() {
		if (this.persistentAttributeInterceptor != null) {
			return (Pagamento) persistentAttributeInterceptor.readObject(this, "Pagamento", this.pagamento);
		}
		return this.pagamento;
	}

	public void setPagamento(Pagamento pagamento) {
		if (this.persistentAttributeInterceptor != null) {
			this.pagamento = (Pagamento) persistentAttributeInterceptor.writeObject(this, "pagamento", this.pagamento,
					pagamento);

		} else {
			this.pagamento = pagamento;
		}
	}

	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	@Transient
	private PersistentAttributeInterceptor persistentAttributeInterceptor;

	@Override
	public PersistentAttributeInterceptor $$_hibernate_getInterceptor() {
		return this.persistentAttributeInterceptor;
	}

	@Override
	public void $$_hibernate_setInterceptor(PersistentAttributeInterceptor persistentAttributeInterceptor) {

		this.persistentAttributeInterceptor = persistentAttributeInterceptor;
	}

	public boolean isPago() {
		return StatusPedido.PAGO.equals(status);
	}

//	@PrePersist
//	@PreUpdate
	public void calcularTotal() {
		if (itens != null) {
			total = itens.stream().map(i -> new BigDecimal(i.getQuantidade()).multiply(i.getPrecoProduto()))
					.reduce(BigDecimal.ZERO, BigDecimal::add);
		} else {
			total = BigDecimal.ZERO;
		}
	}

	@PrePersist
	public void aoPersistir() {
		dataCriacao = LocalDateTime.now();
		calcularTotal();
	}

	@PreUpdate
	public void aoAtualizar() {
		dataUltimaAtualizacao = LocalDateTime.now();
		calcularTotal();
	}

	@PostPersist
	public void aposPersistir() {
		System.out.println("Apos persistir pedido");
	}

	@PostUpdate
	public void aposAtualizar() {
		System.out.println("Apos atualizar Pedido");
	}

	@PreRemove
	private void aoRemover() {
		System.out.println("Antes de remover Pedido");
	}

	@PostLoad
	public void aoCarregar() {
		System.out.println("Apos carregar o Pedido");
	}

}
