package eta.main.modeloEntidad;

//jakarta.persistence.*: Proporciona las anotaciones necesarias para mapear la clase y sus campos a la base de datos.
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

// JPA - Java Persistence API. Es una especificación de Java que permite mapear, almacenar, actualizar y recuperar datos entre objetos Jav

// Marca la clase como entidad JPA y la mapea a la tabla "persona" en bd
@Entity
@Table(name = "persona")
public class Persona {
    // todos los valores son columnas de la tabla persona

    // Clave primaria autoincremental PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//AI
    @Column(name = "id_persona")
    private Long idPersona;

    // Info de Columna "nombre" de hasta 100 caracteres
    @Column(name = "nombre", length = 100)
    private String nombre;

    // Columna "correo_electronico", obligatoria, única y de hasta 100 caracteres
    @Column(name = "correo_electronico", nullable = false, length = 100, unique = true)
    private String correoElectronico;

    // Relación uno a uno con Roles, usando la columna "id_rol"
    @OneToOne
    @JoinColumn(name = "id_rol", referencedColumnName = "id_rol")
    private Roles roles;

    // Constructor vacío requerido por JPA
    public Persona() {}

    // Constructor con parámetros
    public Persona(String nombre, String correoElectronico, Roles roles) {
        this.nombre = nombre;
        this.correoElectronico = correoElectronico;
        this.roles = roles;
    }

    // Getters y Setters
    public Long getIdPersona() { return idPersona; }
    public void setIdPersona(Long idPersona) { this.idPersona = idPersona; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getCorreoElectronico() { return correoElectronico; }
    public void setCorreoElectronico(String correoElectronico) { this.correoElectronico = correoElectronico; }
    public Roles getRoles() { return roles; }
    public void setRoles(Roles roles) { this.roles = roles; }
}