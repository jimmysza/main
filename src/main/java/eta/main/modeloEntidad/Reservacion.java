package eta.main.modeloEntidad;


import jakarta.persistence.*;

@Entity
@Table(name = "reservaciones")
public class Reservacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reservacion")
    private Long idReservacion;

    @Column(name = "fechaReservada")
    private java.sql.Date fechaReservada;

    @Column(name = "horaReservada")
    private java.sql.Time horaReservada;

    @Column(name = "cantidadPersonas")
    private Integer cantidadPersonas;

    @Column(name = "EstadoPago", length = 30)
    private String estadoPago;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_persona", referencedColumnName = "id_persona")
    private Persona persona;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_colaborador_empresa", referencedColumnName = "id_colaborador_empresa")
    private ColaboradorEmpresa colaboradorEmpresa;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_colaborador_individual", referencedColumnName = "id_colaborador_individual")
    private ColaboradorIndividual colaboradorIndividual;

    // Getters y Setters

    public Long getIdReservacion() {
        return idReservacion;
    }

    public void setIdReservacion(Long idReservacion) {
        this.idReservacion = idReservacion;
    }

    public java.sql.Date getFechaReservada() {
        return fechaReservada;
    }

    public void setFechaReservada(java.sql.Date fechaReservada) {
        this.fechaReservada = fechaReservada;
    }

    public java.sql.Time getHoraReservada() {
        return horaReservada;
    }

    public void setHoraReservada(java.sql.Time horaReservada) {
        this.horaReservada = horaReservada;
    }

    public Integer getCantidadPersonas() {
        return cantidadPersonas;
    }

    public void setCantidadPersonas(Integer cantidadPersonas) {
        this.cantidadPersonas = cantidadPersonas;
    }

    public String getEstadoPago() {
        return estadoPago;
    }

    public void setEstadoPago(String estadoPago) {
        this.estadoPago = estadoPago;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public ColaboradorEmpresa getColaboradorEmpresa() {
        return colaboradorEmpresa;
    }

    public void setColaboradorEmpresa(ColaboradorEmpresa colaboradorEmpresa) {
        this.colaboradorEmpresa = colaboradorEmpresa;
    }

    public ColaboradorIndividual getColaboradorIndividual() {
        return colaboradorIndividual;
    }

    public void setColaboradorIndividual(ColaboradorIndividual colaboradorIndividual) {
        this.colaboradorIndividual = colaboradorIndividual;
    }
}
