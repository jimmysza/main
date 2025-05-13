package eta.main.repositorio;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository; // Importa JpaRepository para la gestión de datos.
import org.springframework.stereotype.Repository; // Indica que esta interfaz es un repositorio de Spring.

import eta.main.modeloEntidad.Cliente; // Importa la clase Cliente que es la entidad gestionada.

@Repository // Anotación que marca esta interfaz como un repositorio.
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    //buscar en cliente cuyo usuario coincidad con el proporcionado
    Cliente findByUsuario(String usuario);
    Cliente findByContrasena(String contrasena);
    Cliente findByUsuarioAndContrasena(String usuario, String contrasena);
    List<Cliente> findByPersona_Roles_IdRol(Long idRol);
    


    // JpaRepository ya proporciona métodos comunes como save(), findAll(), findById(), delete(), etc.
    // Puedes agregar métodos personalizados aquí si es necesario, por ejemplo:
    // Cliente findByUsuario(String usuario);
}
