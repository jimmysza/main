package eta.main.modeloEntidad;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "actividad")
public class Actividad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_actividad")
    private Long idActividad;

    @ManyToOne
    @JoinColumn(name = "id_colaborador")
    private Colaborador colaborador;

    @Column(name = "titulo", length = 100)
    private String titulo;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "cupo_maximo")
    private Integer cupoMaximo;

    @Column(name = "ubicacion", length = 100)
    private String ubicacion;

    @Column(name = "categoria", length = 50)
    private String categoria;

    @OneToMany(mappedBy = "actividad", cascade = CascadeType.ALL)
    private List<ActividadFecha> fechas;

    @OneToMany(mappedBy = "actividad", cascade = CascadeType.ALL)
    private List<Plan> planes;

    @OneToMany(mappedBy = "actividad", cascade = CascadeType.ALL)
    private List<Comentario> comentarios;

    public Actividad() {
    }

    public Long getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(Long idActividad) {
        this.idActividad = idActividad;
    }

    public Colaborador getColaborador() {
        return colaborador;
    }

    public void setColaborador(Colaborador colaborador) {
        this.colaborador = colaborador;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getCupoMaximo() {
        return cupoMaximo;
    }

    public void setCupoMaximo(Integer cupoMaximo) {
        this.cupoMaximo = cupoMaximo;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public List<ActividadFecha> getFechas() {
        return fechas;
    }

    public void setFechas(List<ActividadFecha> fechas) {
        this.fechas = fechas;
    }

    public List<Plan> getPlanes() {
        return planes;
    }

    public void setPlanes(List<Plan> planes) {
        this.planes = planes;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

}
