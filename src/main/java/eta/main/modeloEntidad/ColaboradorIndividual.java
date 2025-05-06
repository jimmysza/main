package eta.main.modeloEntidad;

import jakarta.persistence.*;

@Entity
@Table(name = "colaborador_individual")
public class ColaboradorIndividual {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_colaborador_individual") // nombre claro y coherente
    private Long idColaboradorIndividual;

    @Column(name = "RUC", length = 20, nullable = false)
    private String ruc;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_persona", referencedColumnName = "id_persona")
    private Persona persona;

    // Constructor por defecto
    public ColaboradorIndividual() {}

    // Constructor con parámetros
    public ColaboradorIndividual(String ruc, Persona persona) {
        this.ruc = ruc;
        this.persona = persona;
    }

    // Getters y setters
    public Long getIdColaboradorIndividual() {
        return idColaboradorIndividual;
    }

    public void setIdColaboradorIndividual(Long idColaboradorIndividual) {
        this.idColaboradorIndividual = idColaboradorIndividual;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }
}
