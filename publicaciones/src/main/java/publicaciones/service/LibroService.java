package publicaciones.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import publicaciones.dto.LibroDto;
import publicaciones.dto.ResponseDto;
import publicaciones.model.Autor;
import publicaciones.model.Libro;
import publicaciones.repository.AutorRepository;
import publicaciones.repository.LibroRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private NotificacionProducer producer;

    @Autowired
    private CatalogoProducer producerCatalogo;

    public ResponseDto crearLibro(LibroDto libroDto) {
        try {
            Autor autor = autorRepository.findById(libroDto.getIdAutor())
                    .orElseThrow(() -> new RuntimeException("No existe el autor con id: " + libroDto.getIdAutor()));

            Libro libro = new Libro();
            libro.setTitulo(libroDto.getTitulo());
            libro.setAnioPublicacion(libroDto.getAnioPublicacion());
            libro.setResumen(libroDto.getResumen());
            libro.setEditorial(libroDto.getEditorial());
            libro.setIsbn(libroDto.getIsbn());
            libro.setGenero(libroDto.getGenero());
            libro.setNumeroPaginas(libroDto.getNumeroPaginas());
            libro.setAutor(autor);

            Libro libroGuardado = libroRepository.save(libro);
            producer.enviarNotificacion("Libro: " + libroGuardado.getTitulo() + " ha sido registrado", "Libro");

            // Enviar a ms-catalogo
            producerCatalogo.enviarCatalogo(
                    "Libro", // tipo
                    libroGuardado.getTitulo(),
                    libroGuardado.getAnioPublicacion(),
                    libroGuardado.getResumen(),
                    libroGuardado.getEditorial(),
                    libroGuardado.getIsbn()
            );

            return new ResponseDto("Libro creado exitosamente", libroGuardado);

        } catch (Exception e) {
            return new ResponseDto("Error al crear el libro: " + e.getMessage(), null);
        }
    }

    public ResponseDto actualizarLibro(Long id, LibroDto libroDto) {
        try {
            Libro libroExistente = libroRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("No existe el libro con id: " + id));

            Autor autor = autorRepository.findById(libroDto.getIdAutor())
                    .orElseThrow(() -> new RuntimeException("No existe el autor con id: " + libroDto.getIdAutor()));

            libroExistente.setTitulo(libroDto.getTitulo());
            libroExistente.setAnioPublicacion(libroDto.getAnioPublicacion());
            libroExistente.setResumen(libroDto.getResumen());
            libroExistente.setEditorial(libroDto.getEditorial());
            libroExistente.setIsbn(libroDto.getIsbn());
            libroExistente.setGenero(libroDto.getGenero());
            libroExistente.setNumeroPaginas(libroDto.getNumeroPaginas());
            libroExistente.setAutor(autor);

            Libro libroActualizado = libroRepository.save(libroExistente);
            return new ResponseDto("Libro actualizado correctamente", libroActualizado);

        } catch (Exception e) {
            return new ResponseDto("Error al actualizar el libro: " + e.getMessage(), null);
        }
    }

    public List<ResponseDto> listarLibros() {
        return libroRepository.findAll().stream()
                .map(libro -> new ResponseDto("Libro: " + libro.getTitulo(), libro))
                .collect(Collectors.toList());
    }

    public ResponseDto libroPorId(Long id) {
        try {
            Libro libro = libroRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("No existe el libro con id: " + id));
            return new ResponseDto("Libro con id " + libro.getId(), libro);
        } catch (Exception e) {
            return new ResponseDto("Error al buscar el libro: " + e.getMessage(), null);
        }
    }

    public ResponseDto eliminarLibro(Long id) {
        try {
            Libro libroExistente = libroRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("No existe el libro con id: " + id));
            libroRepository.delete(libroExistente);
            return new ResponseDto("Libro eliminado correctamente", null);
        } catch (Exception e) {
            return new ResponseDto("Error al eliminar el libro: " + e.getMessage(), null);
        }
    }

    public List<ResponseDto> listarLibrosPorGenero(String genero) {
        return libroRepository.findAll().stream()
                .filter(libro -> libro.getGenero().equalsIgnoreCase(genero))
                .map(libro -> new ResponseDto("Libro: " + libro.getTitulo(), libro))
                .collect(Collectors.toList());

    }

}
