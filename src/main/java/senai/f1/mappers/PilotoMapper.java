package senai.f1.mappers;

import senai.f1.dtos.request.PilotoRequestDTO;
import senai.f1.dtos.response.PilotoResponseDTO;
import senai.f1.model.Piloto;

public class PilotoMapper {

    public static PilotoResponseDTO toDTO(Piloto entity) {
        return new PilotoResponseDTO(entity.getId(), entity.getNome(), entity.getEquipe());
    }

    public static Piloto toEntity(PilotoRequestDTO dto) {
        Piloto p = new Piloto();
        p.setNome(dto.nome());
        p.setEquipe(dto.equipe());
        return p;
    }
}
