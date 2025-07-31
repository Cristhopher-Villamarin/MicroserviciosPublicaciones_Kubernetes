package publicaciones.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Libro extends Publicacion{

    private String genero;
    private int numeroPaginas;

    public String getGenero() {
        return genero;
    }

    public int getNumeroPaginas() {
        return numeroPaginas;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setNumeroPaginas(int numeroPaginas) {
        this.numeroPaginas = numeroPaginas;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    @ManyToOne
    @JoinColumn(name = "id_autor")
    private Autor autor;
}
