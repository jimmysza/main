package eta.main.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import eta.main.modeloEntidad.ColaboradorIndividual;

@Repository
public interface ColaboradorIndividualRepository extends JpaRepository<ColaboradorIndividual, Long> {
}
