package eta.main.modeloEntidad;

import jakarta.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "actividades")
public class Actividad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_actividad") // nombre claro para el ID
    private Long idActividad;

    @ManyToOne
    @JoinColumn(name = "id_colaborador_empresa", referencedColumnName = "id_colaborador_empresa") // Se ajusta la referencia
    private ColaboradorEmpresa colaboradorEmpresa;

    @ManyToOne
    @JoinColumn(name = "id_colaborador_individual", referencedColumnName = "id_colaborador_individual") // Se ajusta la referencia
    private ColaboradorIndividual colaboradorIndividual;

    @Column(name = "Titulo", length = 100)
    private String titulo;

    @Column(name = "descripcion", length = 500)
    private String descripcion;

    @Column(name = "cupo_maximo")
    private int cupoMaximo;

    @Column(name = "ubicacion", length = 100)
    private String ubicacion;

    @Column(name = "fecha")
    private Date fecha;

    @Column(name = "incluidos", columnDefinition = "JSON")
    private String incluidos;

    @Column(name = "categoria", columnDefinition = "JSON")
    private String categoria;

    @Column(name = "ciudad", length = 30)
    private String ciudad;

    // Constructor por defecto
    public Actividad() {
    }

    // Constructor con parámetros
    public Actividad(ColaboradorEmpresa colaboradorEmpresa, ColaboradorIndividual colaboradorIndividual, String titulo, String descripcion, int cupoMaximo, String ubicacion, Date fecha, String incluidos, String categoria, String ciudad) {
        this.colaboradorEmpresa = colaboradorEmpresa;
        this.colaboradorIndividual = colaboradorIndividual;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.cupoMaximo = cupoMaximo;
        this.ubicacion = ubicacion;
        this.fecha = fecha;
        this.incluidos = incluidos;
        this.categoria = categoria;
        this.ciudad = ciudad;
    }

    // Getters y setters
    public Long getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(Long idActividad) {
        this.idActividad = idActividad;
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

    public int getCupoMaximo() {
        return cupoMaximo;
    }

    public void setCupoMaximo(int cupoMaximo) {
        this.cupoMaximo = cupoMaximo;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getIncluidos() {
        return incluidos;
    }

    public void setIncluidos(String incluidos) {
        this.incluidos = incluidos;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }
}
