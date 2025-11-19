package senai.f1.dtos.request;

import java.util.List;
import java.util.UUID;

public record CorridaRequestDTO(UUID pistaId, List<UUID> podioIds) { }