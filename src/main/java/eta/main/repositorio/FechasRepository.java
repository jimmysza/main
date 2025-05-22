package eta.main.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository; // Importa JpaRepository para la gestión de datos.
import org.springframework.stereotype.Repository;

import eta.main.modeloEntidad.Fechas;

@Repository
public interface FechasRepository extends JpaRepository<Fechas, Long> {

    long countByActividadColaboradorIdColaborador(Long idColaborador);
    List<Fechas> findByActividad_IdActividad(Long idActividad); // Encuentra fechas por actividad.

    List<Fechas> findByActividadColaboradorIdColaborador(Long idColaborador);
    
}
