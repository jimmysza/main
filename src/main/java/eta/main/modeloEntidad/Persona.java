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
@Table(name = "persona") // Se mapea a la tabla "persona"
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_persona")
    private Long idPersona; // Clave primaria autoincremental

    @Column(name = "NombreCompleto", length = 100)
    private String NombreCompleto; // Campo obligatorio

    @Column(name = "Fecha_de_Nacimiento")
    private java.sql.Date fechaDeNacimiento; // Fecha opcional

    @Column(name = "Telefono", length = 20)
    private String telefono; // Teléfono, no obligatorio

    @Column(name = "Correo_Electronico", nullable = false, length = 100, unique = true)
    private String correoElectronico; // Campo obligatorio y único

    @OneToOne
    @JoinColumn(name = "id_rol", referencedColumnName = "id_rol")
    private Roles roles; // Relación con la entidad Roles

    // Constructor vacío requerido por JPA
    public Persona() {}

    // Constructor para crear objetos fácilmente
    public Persona(String NombreCompleto, java.sql.Date fechaDeNacimiento, String telefono, String correoElectronico, Roles roles) {
        this.NombreCompleto = NombreCompleto;
        this.fechaDeNacimiento = fechaDeNacimiento;
        this.telefono = telefono;
        this.correoElectronico = correoElectronico;
        this.roles = roles;
    }

    // Getters y Setters estándar
    public Long getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
    }

    public String getNombreCompleto() {
        return NombreCompleto;
    }

    public void setNombreCompleto(String NombreCompleto) {
        this.NombreCompleto = NombreCompleto;
    }

    public java.sql.Date getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(java.sql.Date fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public Roles getRol() {
        return roles;
    }
    
    public void setRol(Roles roles) {
        this.roles = roles;
    }



}   
