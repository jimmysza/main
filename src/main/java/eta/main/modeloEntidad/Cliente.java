package eta.main.modeloEntidad;

import jakarta.persistence.*;

@Entity
@Table(name = "cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente") // nombre de columna en la base de datos
    private Long idCliente;

    @Column(name = "Preferencias", columnDefinition = "TEXT")
    private String preferencias;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_persona", referencedColumnName = "id_persona")
    private Persona persona;

    // Constructor por defecto
    public Cliente() {}

    // Constructor con parámetros
    public Cliente(String preferencias, Persona persona) {
        this.preferencias = preferencias;
        this.persona = persona;
    }

    // Getters y setters
    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public String getPreferencias() {
        return preferencias;
    }

    public void setPreferencias(String preferencias) {
        this.preferencias = preferencias;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }
}
