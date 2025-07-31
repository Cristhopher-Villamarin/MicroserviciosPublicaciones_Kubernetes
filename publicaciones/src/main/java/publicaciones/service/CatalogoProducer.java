package publicaciones.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import publicaciones.dto.CatalogoDto;
import publicaciones.dto.NotificacionDto;

@Service
public class CatalogoProducer {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void enviarCatalogo(String tipo,String titulo,int anioPublicacion, String resumen,String  editorial,String isbn){
        try{
            CatalogoDto catalogoDto = new CatalogoDto(tipo,titulo,anioPublicacion, resumen, editorial,isbn);
            String json = objectMapper.writeValueAsString(catalogoDto);
            rabbitTemplate.convertAndSend("catalogo.cola", json);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
