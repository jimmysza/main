package eta.main.modeloEntidad;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "persona")
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_persona")
    private Long idPersona;

    @Column(name = "nombre", length = 100)
    private String nombre;

    @Column(name = "correo_electronico", nullable = false, length = 100, unique = true)
    private String correoElectronico;

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
    public Long getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public Roles getRoles() {
        return roles;
    }

    public void setRoles(Roles roles) {
        this.roles = roles;
    }
}
