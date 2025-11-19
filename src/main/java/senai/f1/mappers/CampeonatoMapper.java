package senai.f1.mappers;

import senai.f1.dtos.request.CampeonatoRequestDTO;
import senai.f1.dtos.response.CampeonatoResponseDTO;
import senai.f1.model.Campeonato;
import senai.f1.model.Corrida;

import java.util.List;

public class CampeonatoMapper {

    public static CampeonatoResponseDTO toDTO(Campeonato entity) {
        return new CampeonatoResponseDTO(
                entity.getId(),
                entity.getCorridas().stream().map(CorridaMapper::toDTO).toList()
        );
    }

    public static Campeonato toEntity(CampeonatoRequestDTO dto, List<Corrida> corridas) {
        Campeonato c = new Campeonato();
        c.setCorridas(corridas);
        return c;
    }
}
