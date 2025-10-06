package app.entity;

import java.util.Objects;

import jakarta.persistence.*;

@Entity
@Table(name = "TB_PLANO")
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
		return Objects.hash(descr, id, valorMensal);
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
		return Objects.equals(descr, other.descr) && Objects.equals(id, other.id)
				&& Objects.equals(valorMensal, other.valorMensal);
	}
			
	
}
