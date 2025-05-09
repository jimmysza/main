package eta.main.modeloEntidad;

import jakarta.persistence.*;

@Entity
@Table(name = "colaborador")
public class Colaborador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_colaborador")
    private Long idColaborador;

    @Column(name = "ruc", length = 20)
    private String ruc;

    @Column(name = "usuario", length = 50, unique = true)
    private String usuario;

    @Column(name = "contrasena", length = 255)
    private String contrasena;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_persona", referencedColumnName = "id_persona")
    private Persona persona;

    public Colaborador() {}

    public Long getIdColaborador() {
    return idColaborador;
}

public void setIdColaborador(Long idColaborador) {
    this.idColaborador = idColaborador;
}

public String getRuc() {
    return ruc;
}

public void setRuc(String ruc) {
    this.ruc = ruc;
}

public String getUsuario() {
    return usuario;
}

public void setUsuario(String usuario) {
    this.usuario = usuario;
}

public String getContrasena() {
    return contrasena;
}

public void setContrasena(String contrasena) {
    this.contrasena = contrasena;
}

public Persona getPersona() {
    return persona;
}

public void setPersona(Persona persona) {
    this.persona = persona;
}

}
