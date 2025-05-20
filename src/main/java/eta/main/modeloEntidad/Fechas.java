package eta.main.modeloEntidad;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "fechas")
public class Fechas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_fecha")
    private Long idFecha;

    @ManyToOne
    @JoinColumn(name = "id_actividad", referencedColumnName = "id_actividad")
    private Actividad actividad;

    @Column(name = "dia")
    private LocalDate dia;

    @Column(name = "hora")
    private LocalTime hora;

    // Getters y setters

    public Long getIdFecha() {
        return idFecha;
    }

    public void setIdFecha(Long idFecha) {
        this.idFecha = idFecha;
    }

    public Actividad getActividad() {
        return actividad;
    }

    public void setActividad(Actividad actividad) {
        this.actividad = actividad;
    }

    public LocalDate getDia() {
        return dia;
    }

    public void setDia(LocalDate dia) {
        this.dia = dia;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }
}