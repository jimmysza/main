package eta.main.modeloEntidad;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservaciones")
public class Reservacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reservacion")
    private Long idReservacion;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_actividad_fecha")
    private ActividadFecha actividadFecha;

    @Column(name = "fecha_reservacion")
    private LocalDateTime fechaReservacion;

    public Reservacion() {
        this.fechaReservacion = LocalDateTime.now();
    }

    // Getters y setters
    public Long getIdReservacion() {
        return idReservacion;
    }

    public void setIdReservacion(Long idReservacion) {
        this.idReservacion = idReservacion;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public ActividadFecha getActividadFecha() {
        return actividadFecha;
    }

    public void setActividadFecha(ActividadFecha actividadFecha) {
        this.actividadFecha = actividadFecha;
    }

    public LocalDateTime getFechaReservacion() {
        return fechaReservacion;
    }

    public void setFechaReservacion(LocalDateTime fechaReservacion) {
        this.fechaReservacion = fechaReservacion;
    }
}
