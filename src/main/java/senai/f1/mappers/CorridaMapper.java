package senai.f1.mappers;

import senai.f1.dtos.request.CorridaRequestDTO;
import senai.f1.dtos.response.CorridaResponseDTO;
import senai.f1.model.Corrida;
import senai.f1.model.Piloto;
import senai.f1.model.Pista;

import java.util.List;

public class CorridaMapper {

    public static CorridaResponseDTO toDTO(Corrida entity) {
        return new CorridaResponseDTO(
                entity.getId(),
                PistaMapper.toDTO(entity.getPista()),
                entity.getPodio().stream().map(PilotoMapper::toDTO).toList()
        );
    }

    public static Corrida toEntity(CorridaRequestDTO dto, Pista pista, List<Piloto> pilotos) {
        Corrida c = new Corrida();
        c.setPista(pista);
        c.setPodio(pilotos);
        return c;
    }
}
