package publicaciones.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import publicaciones.dto.PaperDto;
import publicaciones.dto.ResponseDto;
import publicaciones.service.PaperService;
import java.util.List;

@RestController
@RequestMapping("/papers")
public class PaperController {
    @Autowired
    private PaperService paperService;

    @PostMapping
    public ResponseDto crearPaper(@RequestBody PaperDto paperDto) {
        return paperService.crearPaper(paperDto);
    }

    @GetMapping
    public List<ResponseDto> listarPapers() {
        return paperService.listarPapers();
    }

    @GetMapping("/{id}")
    public ResponseDto obtenerPaperPorId(@PathVariable Long id) {
        return paperService.paperPorId(id);
    }

    @PutMapping("/{id}")
    public ResponseDto actualizarPaper(@PathVariable Long id, @RequestBody PaperDto paperDto) {
        return paperService.actualizarPaper(id, paperDto);
    }

    @DeleteMapping("/{id}")
    public ResponseDto eliminarPaper(@PathVariable Long id) {
        return paperService.eliminarPaper(id);
    }

    @GetMapping("/ordenados/fecha")
    public List<ResponseDto> listarPapersOrdenadosPorFecha() {
        return paperService.listarPapersOrdenadosPorFecha();
    }
}
