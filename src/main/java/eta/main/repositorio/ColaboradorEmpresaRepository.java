package eta.main.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import eta.main.modeloEntidad.ColaboradorEmpresa;

@Repository
public interface ColaboradorEmpresaRepository extends JpaRepository<ColaboradorEmpresa, Long> {
}
