package eta.main.modeloEntidad;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comentario")
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comentario")
    private Long idComentario;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_actividad")
    private Actividad actividad;

    @Column(name = "texto", columnDefinition = "TEXT")
    private String texto;

    @Column(name = "calificacion")
    private Integer calificacion;

    @Column(name = "fecha_comentario")
    private LocalDateTime fechaComentario;

    public Comentario() {
        this.fechaComentario = LocalDateTime.now();
    }

    public Long getIdComentario() {
        return idComentario;
    }

    public void setIdComentario(Long idComentario) {
        this.idComentario = idComentario;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Actividad getActividad() {
        return actividad;
    }

    public void setActividad(Actividad actividad) {
        this.actividad = actividad;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Integer getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Integer calificacion) {
        this.calificacion = calificacion;
    }

    public LocalDateTime getFechaComentario() {
        return fechaComentario;
    }

    public void setFechaComentario(LocalDateTime fechaComentario) {
        this.fechaComentario = fechaComentario;
    }

}
