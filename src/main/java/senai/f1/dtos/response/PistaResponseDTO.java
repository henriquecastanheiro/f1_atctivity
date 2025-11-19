package senai.f1.dtos.response;

import java.util.UUID;

public record PistaResponseDTO(UUID id, PaisResponseDTO pais, Double distancia, String dificuldade) { }