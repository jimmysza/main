package eta.main.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eta.main.modeloEntidad.Plan;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {

    long countByActividadColaboradorIdColaborador(Long idColaborador);

    List<Plan> findByActividad_Colaborador_IdColaborador(Long idColaborador);

    List<Plan> findByActividad_IdActividad(Long idActividad);

}
