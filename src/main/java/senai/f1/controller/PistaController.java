package senai.f1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import senai.f1.dtos.request.PistaRequestDTO;
import senai.f1.dtos.response.PistaResponseDTO;
import senai.f1.enums.Dificuldade;
import senai.f1.service.PistaService;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/pistas")
@RequiredArgsConstructor
@Tag(name = "Pistas", description = "Gerenciamento de pistas de Fórmula 1")
public class PistaController {
    private final PistaService pistaService;

    @PostMapping
    @Operation(
            summary = "Cadastrar pista",
            description = "Cria uma nova pista no sistema a partir dos dados fornecidos."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pista cadastrada com sucesso",
                    content = @Content(schema = @Schema(implementation = PistaResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos enviados na requisição")
    })
    public ResponseEntity<PistaResponseDTO> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados necessários para cadastrar uma pista",
                    required = true
            )
            @RequestBody @Valid PistaRequestDTO dto) {
        return ResponseEntity.ok(pistaService.create(dto));
    }

    @GetMapping
    @Operation(summary = "Listar todas as pistas",
            description = "Retorna a lista completa de pistas cadastradas.")
    @ApiResponse(responseCode = "200", description = "Lista de pistas retornada com sucesso")
    public ResponseEntity<List<PistaResponseDTO>> listAll() {
        return ResponseEntity.ok(pistaService.listAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar pista por ID",
            description = "Retorna os dados de uma pista específica pelo seu identificador único (UUID).")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pista encontrada",
                    content = @Content(schema = @Schema(implementation = PistaResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Pista não encontrada")
    })
    public ResponseEntity<PistaResponseDTO> findById(
            @Parameter(description = "ID único da pista", required = true)
            @PathVariable UUID id) {
        return ResponseEntity.ok(pistaService.findById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar pista",
            description = "Atualiza os dados de uma pista existente pelo seu ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pista atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para atualização"),
            @ApiResponse(responseCode = "404", description = "Pista não encontrada")
    })
    public ResponseEntity<PistaResponseDTO> update(
            @Parameter(description = "ID da pista a ser atualizada", required = true)
            @PathVariable UUID id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Novos dados da pista",
                    required = true
            )
            @RequestBody @Valid PistaRequestDTO dto) {
        return ResponseEntity.ok(pistaService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir pista",
            description = "Remove uma pista do sistema com base no seu ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Pista removida com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pista não encontrada")
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID da pista a ser excluída", required = true)
            @PathVariable UUID id) {
        pistaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // 🔎 Consultas customizadas
    @GetMapping("/buscar/pais")
    @Operation(summary = "Buscar pistas por país",
            description = "Retorna todas as pistas localizadas em um determinado país.")
    @ApiResponse(responseCode = "200", description = "Lista de pistas encontrada para o país")
    public ResponseEntity<List<PistaResponseDTO>> findByPais(
            @Parameter(description = "Nome do país", required = true)
            @RequestParam String nome) {
        return ResponseEntity.ok(pistaService.findByPais(nome));
    }

    @GetMapping("/buscar/dificuldade")
    @Operation(summary = "Buscar pistas por dificuldade",
            description = "Retorna todas as pistas filtradas pelo nível de dificuldade.")
    @ApiResponse(responseCode = "200", description = "Lista de pistas encontrada para a dificuldade especificada")
    public ResponseEntity<List<PistaResponseDTO>> findByDificuldade(
            @Parameter(
                    description = "Nível de dificuldade da pista. Valores possíveis: FACIL, MEDIO, DIFICIL.",
                    required = true,
                    schema = @Schema(implementation = Dificuldade.class)
            )
            @RequestParam Dificuldade dificuldade) {
        return ResponseEntity.ok(pistaService.findByDificuldade(dificuldade));
    }
}
