package senai.f1.mappers;

import senai.f1.dtos.request.PistaRequestDTO;
import senai.f1.dtos.response.PistaResponseDTO;
import senai.f1.enums.Dificuldade;
import senai.f1.model.Pais;
import senai.f1.model.Pista;

public class PistaMapper {

    public static PistaResponseDTO toDTO(Pista entity) {
        return new PistaResponseDTO(
                entity.getId(),
                PaisMapper.toDTO(entity.getPais()),
                entity.getDistancia(),
                entity.getDificuldade().name()
        );
    }

    public static Pista toEntity(PistaRequestDTO dto, Pais pais) {
        Pista p = new Pista();
        p.setPais(pais);
        p.setDistancia(dto.distancia());
        p.setDificuldade(Dificuldade.valueOf(dto.dificuldade()));
        return p;
    }
}
