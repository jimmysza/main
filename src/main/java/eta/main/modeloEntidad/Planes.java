package eta.main.modeloEntidad;

import jakarta.persistence.*;

@Entity
@Table(name = "planes")
public class Planes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_planes")
    private Long idPlanes;

    @Column(name = "Titulo", length = 100)
    private String titulo;

    @Column(name = "precio")
    private Integer precio;

    @Column(name = "estado", length = 20)
    private String estado;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_actividad", referencedColumnName = "id_actividad")
    private Actividad actividad;

    @Column(name = "incluidos", columnDefinition = "json")
    private String incluidos;

    // Getters y Setters

    public Long getIdPlanes() {
        return idPlanes;
    }

    public void setIdPlanes(Long idPlanes) {
        this.idPlanes = idPlanes;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getPrecio() {
        return precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Actividad getActividad() {
        return actividad;
    }

    public void setActividad(Actividad actividad) {
        this.actividad = actividad;
    }

    public String getIncluidos() {
        return incluidos;
    }

    public void setIncluidos(String incluidos) {
        this.incluidos = incluidos;
    }
}
