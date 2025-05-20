package eta.main.repositorio;

import org.springframework.data.jpa.repository.JpaRepository; // Importa JpaRepository para la gestión de datos.
import org.springframework.stereotype.Repository;

import eta.main.modeloEntidad.Fechas;

@Repository
public interface FechasRepository extends JpaRepository<Fechas, Long> {

    
}
