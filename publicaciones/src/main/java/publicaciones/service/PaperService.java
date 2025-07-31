package publicaciones.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import publicaciones.dto.PaperDto;
import publicaciones.dto.ResponseDto;
import publicaciones.model.Autor;
import publicaciones.model.Paper;
import publicaciones.repository.AutorRepository;
import publicaciones.repository.PaperRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaperService {
    @Autowired
    private PaperRepository paperRepository;

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private NotificacionProducer producer;

    @Autowired
    private CatalogoProducer producerCatalogo;

    public ResponseDto crearPaper(PaperDto paperDto) {
        try {
            Autor autor = autorRepository.findById(paperDto.getIdAutor())
                    .orElseThrow(() -> new RuntimeException("No existe el autor con id: " + paperDto.getIdAutor()));

            Paper paper = new Paper();
            paper.setTitulo(paperDto.getTitulo());
            paper.setAnioPublicacion(paperDto.getAnioPublicacion());
            paper.setResumen(paperDto.getResumen());
            paper.setEditorial(paperDto.getEditorial());
            paper.setIsbn(paperDto.getIsbn());
            paper.setOrcid(paperDto.getOrcid());
            paper.setFechaPublicacion(paperDto.getFechaPublicacion());
            paper.setRevista(paperDto.getRevista());
            paper.setAreaInvestigacion(paperDto.getAreaInvestigacion());
            paper.setAutor(autor);

            Paper paperGuardado = paperRepository.save(paper);
            producer.enviarNotificacion("Paper: " + paperGuardado.getTitulo() + " ha sido registrado", "Paper");
            // Enviar al catálogo como "Articulo"
            producerCatalogo.enviarCatalogo(
                    "Articulo", // tipo
                    paperGuardado.getTitulo(),
                    paperGuardado.getAnioPublicacion(),
                    paperGuardado.getResumen(),
                    paperGuardado.getEditorial(),
                    paperGuardado.getIsbn()
            );

            return new ResponseDto("Paper creado exitosamente", paperGuardado);


        } catch (Exception e) {
            return new ResponseDto("Error al crear el paper: " + e.getMessage(), null);
        }
    }

    public ResponseDto actualizarPaper(Long id, PaperDto paperDto) {
        try {
            Paper paperExistente = paperRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("No existe el paper con id: " + id));

            Autor autor = autorRepository.findById(paperDto.getIdAutor())
                    .orElseThrow(() -> new RuntimeException("No existe el autor con id: " + paperDto.getIdAutor()));

            paperExistente.setTitulo(paperDto.getTitulo());
            paperExistente.setAnioPublicacion(paperDto.getAnioPublicacion());
            paperExistente.setResumen(paperDto.getResumen());
            paperExistente.setEditorial(paperDto.getEditorial());
            paperExistente.setIsbn(paperDto.getIsbn());
            paperExistente.setOrcid(paperDto.getOrcid());
            paperExistente.setFechaPublicacion(paperDto.getFechaPublicacion());
            paperExistente.setRevista(paperDto.getRevista());
            paperExistente.setAreaInvestigacion(paperDto.getAreaInvestigacion());
            paperExistente.setAutor(autor);

            Paper paperActualizado = paperRepository.save(paperExistente);
            producer.enviarNotificacion("Paper: " + paperActualizado.getTitulo() + " ha sido actualizado", "Paper");
            return new ResponseDto("Paper actualizado correctamente", paperActualizado);

        } catch (Exception e) {
            return new ResponseDto("Error al actualizar el paper: " + e.getMessage(), null);
        }
    }

    public List<ResponseDto> listarPapers() {
        return paperRepository.findAll().stream()
                .map(paper -> new ResponseDto("Paper: " + paper.getTitulo(), paper))
                .collect(Collectors.toList());
    }

    public ResponseDto paperPorId(Long id) {
        try {
            Paper paper = paperRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("No existe el paper con id: " + id));
            return new ResponseDto("Paper con id " + paper.getId(), paper);
        } catch (Exception e) {
            return new ResponseDto("Error al buscar el paper: " + e.getMessage(), null);
        }
    }

    public ResponseDto eliminarPaper(Long id) {
        try {
            Paper paperExistente = paperRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("No existe el paper con id: " + id));
            paperRepository.delete(paperExistente);
            producer.enviarNotificacion("Paper: " + paperExistente.getTitulo() + " ha sido eliminado", "Paper");
            return new ResponseDto("Paper eliminado correctamente", null);
        } catch (Exception e) {
            return new ResponseDto("Error al eliminar el paper: " + e.getMessage(), null);
        }
    }

    public List<ResponseDto> listarPapersOrdenadosPorFecha() {
        return paperRepository.findAll().stream()
                .sorted(Comparator.comparing(Paper::getFechaPublicacion, Comparator.reverseOrder()))
                .map(paper -> new ResponseDto("Paper: " + paper.getTitulo(), paper))
                .collect(Collectors.toList());
    }
}
