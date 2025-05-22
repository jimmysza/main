package eta.main.repositorio;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eta.main.modeloEntidad.Reservacion;

@Repository
public interface ReservacionRepository extends JpaRepository<Reservacion, Long> {

    long countByActividadColaboradorIdColaborador(Long idColaborador);
    List<Reservacion> findByActividadColaboradorIdColaborador(Long idColaborador);
    List<Reservacion> findByClienteIdCliente(Long idCliente);

}
