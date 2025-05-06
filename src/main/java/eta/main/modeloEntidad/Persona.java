package eta.main.modeloEntidad;

import jakarta.persistence.*;

@Entity
@Table(name = "persona") // Se mapea a la tabla "persona"
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_persona")
    private Long idPersona; // Clave primaria autoincremental

    @Column(name = "NombreCompleto", nullable = false, length = 100)
    private String nombreCompleto; // Campo obligatorio

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
    public Persona(String nombreCompleto, java.sql.Date fechaDeNacimiento, String telefono, String correoElectronico, Roles roles) {
        this.nombreCompleto = nombreCompleto;
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
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
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

    public void setRol(Roles rol) {
        this.roles = rol;
    }
}
