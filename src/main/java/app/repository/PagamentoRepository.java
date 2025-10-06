package app.repository;

import app.entity.Pagamento;
import app.enums.StatusPagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {

	
	List<Pagamento> findByAlunoId(Long alunoId);

	
	List<Pagamento> findByStatus(StatusPagamento status);

	
	List<Pagamento> findByDataPagamentoBetween(LocalDateTime inicio, LocalDateTime fim);
	
}
