package eta.main.repositorio;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import eta.main.modeloEntidad.Actividad;

@Repository
public interface  ActividadRepository extends JpaRepository<Actividad, Long> {
}

