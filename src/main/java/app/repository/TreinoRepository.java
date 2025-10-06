package app.repository;

import app.entity.Treino;
import app.enums.NivelTreino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TreinoRepository extends JpaRepository<Treino, Long> {
	
	List<Treino> findByNivel(NivelTreino nivel);

	List<Treino> findByAlunosId(Long alunoId);
	
}
