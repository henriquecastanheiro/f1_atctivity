package senai.f1.dtos.response;

import java.util.List;
import java.util.UUID;

public record CampeonatoResponseDTO(UUID id, List<CorridaResponseDTO> corridas) { }
