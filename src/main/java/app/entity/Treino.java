package app.entity;

import app.enums.NivelTreino;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "TB_TREINO")
public class Treino {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 120)
	private String descr;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private NivelTreino nivel;

	@ManyToMany(mappedBy = "treinos", fetch = FetchType.LAZY)
	private Set<Aluno> alunos = new HashSet<>();

	public Treino() {
		this.nivel = NivelTreino.INICIANTE;
	}

	public Treino(String descr, NivelTreino nivel) {
		this.descr = descr;
		this.nivel = (nivel != null ? nivel : NivelTreino.INICIANTE);
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

	public NivelTreino getNivel() {
		return nivel;
	}

	public void setNivel(NivelTreino nivel) {
		this.nivel = nivel;
	}

	public Set<Aluno> getAlunos() {
		return alunos;
	}

	public void setAlunos(Set<Aluno> alunos) {
		this.alunos = alunos;
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
		Treino other = (Treino) obj;
		return Objects.equals(id, other.id);
	}

}
