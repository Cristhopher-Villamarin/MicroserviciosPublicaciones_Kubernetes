package publicaciones.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import publicaciones.dto.AutorDto;
import publicaciones.dto.ResponseDto;
import publicaciones.service.AutorService;

import java.util.List;

@RestController
@RequestMapping("/autores")
public class AutorController {

    @Autowired
    private AutorService autorService;

    @PostMapping
    public ResponseDto crearAutor(@RequestBody AutorDto dto){
        return autorService.crearAutor(dto);
    }

    @GetMapping
    public List<ResponseDto> obtenerAutores(){
        return autorService.listarAutores();
    }

    @GetMapping("/{id}")
    public ResponseDto obtenerAutorPorId(@PathVariable Long id) {
        return autorService.autorPorId(id);
    }

    @PutMapping("/{id}")
    public ResponseDto actualizarAutor(@PathVariable Long id, @RequestBody AutorDto dto) {
        return autorService.actualizarAutor(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseDto eliminarAutor(@PathVariable Long id) {
        return autorService.eliminarAutor(id);
    }
}
