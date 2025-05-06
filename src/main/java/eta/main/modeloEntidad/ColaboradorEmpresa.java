package eta.main.modeloEntidad;

import jakarta.persistence.*;

@Entity
@Table(name = "colaborador_empresa")
public class ColaboradorEmpresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_colaborador_empresa") // nombre claro y coherente
    private Long idColaboradorEmpresa;

    @Column(name = "nombre_empresa", length = 100, nullable = false)
    private String nombreEmpresa;

    @Column(name = "ruc", length = 20, nullable = false)
    private String ruc;

    @Column(name = "telefono_Empresa", length = 20)
    private String telefonoEmpresa;

    @Column(name = "direccion_Empresa", columnDefinition = "TEXT")
    private String direccionEmpresa;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_persona", referencedColumnName = "id_persona")
    private Persona persona;

    // Constructor por defecto
    public ColaboradorEmpresa() {}

    // Constructor con parámetros
    public ColaboradorEmpresa(String nombreEmpresa, String ruc, String telefonoEmpresa, String direccionEmpresa, Persona persona) {
        this.nombreEmpresa = nombreEmpresa;
        this.ruc = ruc;
        this.telefonoEmpresa = telefonoEmpresa;
        this.direccionEmpresa = direccionEmpresa;
        this.persona = persona;
    }

    // Getters y setters
    public Long getIdColaboradorEmpresa() {
        return idColaboradorEmpresa;
    }

    public void setIdColaboradorEmpresa(Long idColaboradorEmpresa) {
        this.idColaboradorEmpresa = idColaboradorEmpresa;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getTelefonoEmpresa() {
        return telefonoEmpresa;
    }

    public void setTelefonoEmpresa(String telefonoEmpresa) {
        this.telefonoEmpresa = telefonoEmpresa;
    }

    public String getDireccionEmpresa() {
        return direccionEmpresa;
    }

    public void setDireccionEmpresa(String direccionEmpresa) {
        this.direccionEmpresa = direccionEmpresa;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }
}
