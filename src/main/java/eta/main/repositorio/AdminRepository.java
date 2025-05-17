package eta.main.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eta.main.modeloEntidad.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    // Buscar en admin cuyo usuario coincida con el proporcionado
    Admin findByUsuario(String usuario);

    Admin findByContrasena(String contrasena);

    Admin findByUsuarioAndContrasena(String usuario, String contrasena);

    List<Admin> findByPersona_Roles_IdRol(Long idRol);

    // Métodos comunes como save(), findAll(), findById(), delete(), etc. ya están disponibles.
}