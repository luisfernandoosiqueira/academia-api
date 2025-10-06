package app.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "TB_PLANO")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Plano {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 120)
	private String descr;

	@Column(nullable = false)
	private Double valorMensal;

	public Plano() {
		this.valorMensal = 0.0;
	}

	public Plano(String descr, Double valorMensal) {
		this.descr = descr;
		this.valorMensal = (valorMensal != null ? valorMensal : 0.0);
	}

	public Long getId() {
		return id;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public Double getValorMensal() {
		return valorMensal;
	}

	public void setValorMensal(Double valorMensal) {
		this.valorMensal = valorMensal;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Plano other = (Plano) obj;
		return Objects.equals(id, other.id);
	}
}
