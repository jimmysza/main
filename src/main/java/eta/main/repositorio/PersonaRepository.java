package eta.main.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eta.main.modeloEntidad.Persona;// la importa para usarla
//interfaz q permite realizar operaciones CRUD en la tabla persona
// JpaRepository es una interfaz de Spring Data JPA que proporciona métodos para realizar operaciones CRUD y consultas en la base de datos.


// Anotación que indica que esta interfaz es un componente de repositorio de Spring. | buena practica
@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {

    // Busca una persona por su correo electrónico
    Persona findByCorreoElectronico(String correoElectronico);
        // retorna un objeto Persona que coincide con el correo electrónico proporcionado. o null


    // Busca todas las personas con un id de rol específico    
    List<Persona> findByRoles_IdRol(Long idRol); 
        // retorna una lista de objetos Persona que tienen el id de rol especificado. o vacia
}










