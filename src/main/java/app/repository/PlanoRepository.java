package app.repository;

import app.entity.Plano;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanoRepository extends JpaRepository<Plano, Long> {

	List<Plano> findByDescrContainingIgnoreCase(String descr);

	boolean existsByDescr(String descr);
	
}
