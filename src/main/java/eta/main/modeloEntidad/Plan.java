package eta.main.modeloEntidad;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "plan")
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_plan")
    private Long idPlan;

    @Column(name = "titulo", length = 100)
    private String titulo;

    @Column(name = "precio", precision = 10, scale = 2)
    private BigDecimal precio;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "id_actividad")
    private Actividad actividad;

    public Plan() {
    }

    public Long getIdPlan() {
        return idPlan;
    }

    public void setIdPlan(Long idPlan) {
        this.idPlan = idPlan;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Actividad getActividad() {
        return actividad;
    }

    public void setActividad(Actividad actividad) {
        this.actividad = actividad;
    }

}
