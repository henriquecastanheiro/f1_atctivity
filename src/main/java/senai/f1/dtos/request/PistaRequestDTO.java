package senai.f1.dtos.request;

import java.util.UUID;

public record PistaRequestDTO(UUID paisId, Double distancia, String dificuldade) { }