package publicaciones.dto;

import lombok.*;

@Data

public class CatalogoDto {
    private String tipo;
    private String titulo;
    private int anioPublicacion;
    private String resumen;
    private String editorial;
    private String isbn;

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getAnioPublicacion() {
        return anioPublicacion;
    }

    public void setAnioPublicacion(int anioPublicacion) {
        this.anioPublicacion = anioPublicacion;
    }

    public String getResumen() {
        return resumen;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public CatalogoDto(String tipo, String titulo, int anioPublicacion, String resumen, String editorial, String isbn) {
        this.tipo = tipo;
        this.titulo = titulo;
        this.anioPublicacion = anioPublicacion;
        this.resumen = resumen;
        this.editorial = editorial;
        this.isbn = isbn;
    }

    public CatalogoDto() {
    }
}
