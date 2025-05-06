package eta.main.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import eta.main.modeloEntidad.Comentario;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
}

