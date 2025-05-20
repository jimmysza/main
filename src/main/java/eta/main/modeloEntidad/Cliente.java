package eta.main.modeloEntidad;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 * Clase Cliente que representa una entidad en la base de datos.
 * Esta clase está mapeada a la tabla "cliente" y contiene información relacionada
 * con el cliente como su usuario, contraseña y su persona asociada.
 */
@Entity
@Table(name = "cliente") // Mapea esta clase a la tabla "cliente" en la base de datos.
public class Cliente {

    // Campo que representa la clave primaria en la base de datos.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generación automática de ID por la base de datos.
    @Column(name = "id_cliente") // Mapea este campo a la columna "id_cliente" en la base de datos.
    private Long idCliente;

    // Campo que representa el nombre de usuario del cliente.
    @Column(name = "usuario", length = 50) // Mapea este campo a la columna "usuario" con una longitud máxima de 50 caracteres.
    private String usuario;

    // Campo que representa la contraseña del cliente.
    @Column(name = "contrasena", length = 255) // Mapea este campo a la columna "contrasena" con una longitud máxima de 255 caracteres.
    private String contrasena;

    // Relación uno a uno con la entidad Persona.
    @OneToOne(cascade = CascadeType.ALL) // Relación de tipo "uno a uno" con la entidad Persona.
    @JoinColumn(name = "id_persona", referencedColumnName = "id_persona") 
    private Persona persona;
    

    // Constructor por defecto (requerido por JPA).
    public Cliente() {}

    /**
     * Constructor con parámetros para inicializar un cliente con usuario, contraseña y persona.
     * 
     * @param usuario Nombre de usuario del cliente.
     * @param contrasena Contraseña del cliente.
     * @param persona Persona asociada al cliente.
     */
    public Cliente(String usuario, String contrasena, Persona persona) {
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.persona = persona;
    }

    // Getters y setters para acceder y modificar los valores de los campos.

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }
}
