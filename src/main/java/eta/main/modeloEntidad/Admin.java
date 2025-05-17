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
 * Clase Admin que representa una entidad en la base de datos.
 * Esta clase está mapeada a la tabla "admin" y contiene información relacionada
 * con el admin como su usuario, contraseña y su persona asociada.
 */
@Entity
@Table(name = "admin") // Mapea esta clase a la tabla "admin" en la base de datos.
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_admin")
    private Long idAdmin;

    @Column(name = "usuario", length = 50)
    private String usuario;

    @Column(name = "contrasena", length = 50)
    private String contrasena;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_persona", referencedColumnName = "id_persona")
    private Persona persona;

    public Admin() {}

    public Admin(String usuario, String contrasena, Persona persona) {
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.persona = persona;
    }

    public Long getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(Long idAdmin) {
        this.idAdmin = idAdmin;
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