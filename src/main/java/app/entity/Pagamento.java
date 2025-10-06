package app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import app.enums.FormaPagamento;
import app.enums.StatusPagamento;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "TB_PAGAMENTO")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Pagamento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private LocalDateTime dataPagamento;

	@Column(nullable = false)
	private Double valorPago;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 12)
	private StatusPagamento status;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 12)
	private FormaPagamento formaPagamento;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "aluno_id")
	@JsonIgnore
	private Aluno aluno;

	public Pagamento() {
		this.dataPagamento = LocalDateTime.now();
		this.valorPago = 0.0;
		this.status = StatusPagamento.PENDENTE;
		this.formaPagamento = FormaPagamento.PIX;
	}

	public Pagamento(Aluno aluno, Double valorPago, StatusPagamento status, FormaPagamento formaPagamento,
			LocalDateTime dataPagamento) {
		this.aluno = aluno;
		this.valorPago = (valorPago != null ? valorPago : 0.0);
		this.status = (status != null ? status : StatusPagamento.PENDENTE);
		this.formaPagamento = (formaPagamento != null ? formaPagamento : FormaPagamento.PIX);
		this.dataPagamento = (dataPagamento != null ? dataPagamento : LocalDateTime.now());
	}

	public Long getId() {
		return id;
	}

	public LocalDateTime getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(LocalDateTime dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public Double getValorPago() {
		return valorPago;
	}

	public void setValorPago(Double valorPago) {
		this.valorPago = valorPago;
	}

	public StatusPagamento getStatus() {
		return status;
	}

	public void setStatus(StatusPagamento status) {
		this.status = status;
	}

	public FormaPagamento getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(FormaPagamento formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Pagamento other = (Pagamento) obj;
		return Objects.equals(id, other.id);
	}
}
