package eta.main.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eta.main.modeloEntidad.Colaborador;

@Repository
public interface ColaboradorRepository extends JpaRepository<Colaborador, Long>{
    
}
