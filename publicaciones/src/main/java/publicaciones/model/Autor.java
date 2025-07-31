package publicaciones.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;

@Entity
@Table(name = "autor")
@Getter
@Setter
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    private String nombre;

    @Column(name = "lastname", nullable = false)
    private String apellido;
    private String nacionalidad;
    private String institucion;

    @Column(name = "email", nullable = false, length = 50, unique = true)
    private String email;

    @Column(name = "orcid", nullable = false, length = 50, unique = true)
    private String orcid;

    @OneToMany(mappedBy = "autor")
    @JsonIgnore
    private List<Libro> libro;

    @OneToMany(mappedBy = "autor")
    @JsonIgnore
    private List<Paper> articulos;

    public Long getId() {
        return id;
    }

    public String getApellido() {
        return apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public String getInstitucion() {
        return institucion;
    }

    public String getEmail() {
        return email;
    }

    public String getOrcid() {
        return orcid;
    }

    public List<Paper> getArticulos() {
        return articulos;
    }

    public List<Libro> getLibro() {
        return libro;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public void setInstitucion(String institucion) {
        this.institucion = institucion;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setOrcid(String orcid) {
        this.orcid = orcid;
    }

    public void setLibro(List<Libro> libro) {
        this.libro = libro;
    }

    public void setArticulos(List<Paper> articulos) {
        this.articulos = articulos;
    }

}
