package eta.main.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eta.main.modeloEntidad.Reservacion;

@Repository
public interface ReservacionRepository extends JpaRepository<Reservacion, Long> {
}
