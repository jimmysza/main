package eta.main.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
// Importa JpaRepository, una interfaz de Spring Data JPA que proporciona métodos CRUD genéricos.

import org.springframework.stereotype.Repository;
// Marca esta interfaz como un componente tipo repositorio para que Spring lo detecte y gestione automáticamente.}

import eta.main.modeloEntidad.Persona;
// Importa la clase 'Persona', que es la entidad que esta interfaz va a gestionar (persistir, buscar, eliminar, etc.).



// Anotación que indica que esta interfaz es un componente de repositorio de Spring. | buena practica
@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {
    // Declara la interfaz del repositorio para la entidad 'Persona'.
// Hereda de JpaRepository, lo que permite acceder automáticamente a métodos como:
// - findAll()
// - findById(Long id)
// - save(Persona persona)
// - deleteById(Long id), etc.
//
// El primer parámetro es la entidad que gestiona (Persona).
// El segundo parámetro es el tipo de la clave primaria
}










