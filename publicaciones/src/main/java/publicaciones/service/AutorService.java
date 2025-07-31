package publicaciones.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import publicaciones.dto.AutorDto;
import publicaciones.dto.ResponseDto;
import publicaciones.model.Autor;
import publicaciones.repository.AutorRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AutorService {

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private NotificacionProducer producer;


    public ResponseDto crearAutor(AutorDto autorDto) {
        Autor autor = new Autor();
        autor.setNombre(autorDto.getNombre());
        autor.setApellido(autorDto.getApellido());
        autor.setNacionalidad(autorDto.getNacionalidad());
        autor.setInstitucion(autorDto.getInstitucion());
        autor.setEmail(autorDto.getEmail());
        autor.setOrcid(autorDto.getOrcid());

        try {
            Autor autorGuardado = autorRepository.save(autor);
            producer.enviarNotificacion("Autor: " + autorGuardado.getNombre() + " ha sido registrado", "Autor");
            return new ResponseDto("Autor creado exitosamente", autorGuardado);
        } catch (Exception e) {
            return new ResponseDto("Error al crear el autor: " + e.getMessage(), null);
        }
    }


    public ResponseDto actualizarAutor(Long id, AutorDto autorDto) {
        try {
            Autor autorExistente = autorRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("No existe el autor con id: " + id));

            autorExistente.setNombre(autorDto.getNombre());
            autorExistente.setApellido(autorDto.getApellido());
            autorExistente.setNacionalidad(autorDto.getNacionalidad());
            autorExistente.setInstitucion(autorDto.getInstitucion());
            autorExistente.setEmail(autorDto.getEmail());
            autorExistente.setOrcid(autorDto.getOrcid());

            Autor autorActualizado = autorRepository.save(autorExistente);
            return new ResponseDto("Autor actualizado correctamente", autorActualizado);
        } catch (Exception e) {
            return new ResponseDto("Error al actualizar el autor: " + e.getMessage(), null);
        }
    }

    public List<ResponseDto> listarAutores() {
        return autorRepository.findAll().stream()
                .map(autor -> new ResponseDto("Autor: " + autor.getApellido(), autor))
                .collect(Collectors.toList());
    }

    public ResponseDto autorPorId(Long id){
        Autor autor = autorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe el autor con id" + id));
        return new ResponseDto("Autor con id " + autor.getId(),autor);
    }

    public ResponseDto eliminarAutor(Long id) {
        try {
            Autor autorExistente = autorRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("No existe el autor con id: " + id));
            autorRepository.delete(autorExistente);
            return new ResponseDto("Autor eliminado correctamente", null);
        } catch (Exception e) {
            return new ResponseDto("Error al eliminar el autor: " + e.getMessage(), null);
        }
    }

}



