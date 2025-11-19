package senai.f1.dtos.response;

import java.util.List;
import java.util.UUID;

public record CorridaResponseDTO(UUID id, PistaResponseDTO pista, List<PilotoResponseDTO> podio) { }