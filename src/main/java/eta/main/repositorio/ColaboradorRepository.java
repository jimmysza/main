package eta.main.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eta.main.modeloEntidad.Colaborador;

@Repository
public interface ColaboradorRepository extends JpaRepository<Colaborador, Long> {

    Colaborador findByUsuario(String usuario);

    Colaborador findByContrasena(String contrasena);

    Colaborador findByIdentificacion(String identificacion);

    Colaborador findByUsuarioAndContrasena(String usuario, String contrasena);

    List<Colaborador> findByPersona_Roles_IdRol(Long idRol);
}
