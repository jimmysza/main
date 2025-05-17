package eta.main.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eta.main.modeloEntidad.Actividad;

@Repository
public interface ActividadRepository extends JpaRepository<Actividad, Long> {

    long countByColaborador_IdColaborador(Long idColaborador);

    List<Actividad> findByColaborador_IdColaborador(Long idColaborador);
}
