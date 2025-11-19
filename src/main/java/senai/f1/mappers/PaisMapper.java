package senai.f1.mappers;

import senai.f1.dtos.request.PaisRequestDTO;
import senai.f1.dtos.response.PaisResponseDTO;
import senai.f1.model.Pais;

public class PaisMapper {

    public static PaisResponseDTO toDTO(Pais entity) {
        return new PaisResponseDTO(entity.getId(), entity.getNome());
    }

    public static Pais toEntity(PaisRequestDTO dto) {
        Pais p = new Pais();
        p.setNome(dto.nome());
        return p;
    }
}
