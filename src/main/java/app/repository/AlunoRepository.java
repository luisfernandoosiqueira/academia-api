package app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.entity.Aluno;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {

	Optional<Aluno> findByCpf(String cpf);

	boolean existsByCpf(String cpf);

	List<Aluno> findByAtivo(boolean ativo);

	List<Aluno> findByPlanoId(Long id);

	List<Aluno> findByTreinosId(Long treinoId);

}
