package eta.main.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import eta.main.modeloEntidad.Roles;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Long> {
}
