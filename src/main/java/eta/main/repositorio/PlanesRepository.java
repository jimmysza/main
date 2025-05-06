package eta.main.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import eta.main.modeloEntidad.Planes;

@Repository
public interface PlanesRepository extends JpaRepository<Planes, Long> {
}
