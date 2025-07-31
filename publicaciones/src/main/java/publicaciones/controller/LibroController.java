package publicaciones.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import publicaciones.dto.LibroDto;
import publicaciones.dto.ResponseDto;
import publicaciones.service.LibroService;

import java.util.List;

@RestController
@RequestMapping("/libros")
public class LibroController {

    @Autowired
    private LibroService libroService;

    @PostMapping
    public ResponseDto crearLibro(@RequestBody LibroDto libroDto) {
        return libroService.crearLibro(libroDto);
    }

    @GetMapping
    public List<ResponseDto> listarLibros() {
        return libroService.listarLibros();
    }

    @GetMapping("/{id}")
    public ResponseDto obtenerLibroPorId(@PathVariable Long id) {
        return libroService.libroPorId(id);
    }

    @PutMapping("/{id}")
    public ResponseDto actualizarLibro(@PathVariable Long id, @RequestBody LibroDto libroDto) {
        return libroService.actualizarLibro(id, libroDto);
    }

    @DeleteMapping("/{id}")
    public ResponseDto eliminarLibro(@PathVariable Long id) {
        return libroService.eliminarLibro(id);
    }

    @GetMapping("/genero/{genero}")
    public List<ResponseDto> listarLibrosPorGenero(@PathVariable String genero) {
        return libroService.listarLibrosPorGenero(genero);
    }
}
