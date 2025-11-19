package senai.f1.dtos.response;

import java.util.UUID;

public record PilotoResponseDTO(UUID id, String nome, String equipe) { }