package eta.main.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eta.main.modeloEntidad.Persona;
// Importa la clase 'Persona', que es la entidad que esta interfaz va a gestionar (persistir, buscar, eliminar, etc.).



// Anotación que indica que esta interfaz es un componente de repositorio de Spring. | buena practica
@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {

    Persona findByCorreoElectronico(String correoElectronico);
    List<Persona> findByRoles_IdRol(Long idRol); 
}










