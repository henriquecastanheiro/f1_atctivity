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
import senai.f1.dtos.request.CorridaRequestDTO;
import senai.f1.dtos.response.CorridaResponseDTO;
import senai.f1.service.CorridaService;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/corridas")
@RequiredArgsConstructor
@Tag(name = "Corridas", description = "Gerenciamento de corridas de Fórmula 1")
public class CorridaController {
    private final CorridaService corridaService;

    @PostMapping
    @Operation(
            summary = "Cadastrar corrida",
            description = "Cria uma nova corrida no sistema a partir dos dados fornecidos."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Corrida cadastrada com sucesso",
                    content = @Content(schema = @Schema(implementation = CorridaResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos enviados na requisição")
    })
    public ResponseEntity<CorridaResponseDTO> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados para criação de uma corrida",
                    required = true
            )
            @RequestBody @Valid CorridaRequestDTO dto) {
        return ResponseEntity.ok(corridaService.create(dto));
    }

    @GetMapping
    @Operation(summary = "Listar todas as corridas",
            description = "Retorna a lista completa de corridas cadastradas.")
    @ApiResponse(responseCode = "200", description = "Lista de corridas retornada com sucesso")
    public ResponseEntity<List<CorridaResponseDTO>> listAll() {
        return ResponseEntity.ok(corridaService.listAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar corrida por ID",
            description = "Retorna os dados de uma corrida específica pelo seu identificador único (UUID).")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Corrida encontrada",
                    content = @Content(schema = @Schema(implementation = CorridaResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Corrida não encontrada")
    })
    public ResponseEntity<CorridaResponseDTO> findById(
            @Parameter(description = "ID único da corrida", required = true)
            @PathVariable UUID id) {
        return ResponseEntity.ok(corridaService.findById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar corrida",
            description = "Atualiza os dados de uma corrida existente pelo seu ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Corrida atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para atualização"),
            @ApiResponse(responseCode = "404", description = "Corrida não encontrada")
    })
    public ResponseEntity<CorridaResponseDTO> update(
            @Parameter(description = "ID da corrida a ser atualizada", required = true)
            @PathVariable UUID id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Novos dados da corrida",
                    required = true
            )
            @RequestBody @Valid CorridaRequestDTO dto) {
        return ResponseEntity.ok(corridaService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir corrida",
            description = "Remove uma corrida do sistema com base no seu ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Corrida removida com sucesso"),
            @ApiResponse(responseCode = "404", description = "Corrida não encontrada")
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID da corrida a ser excluída", required = true)
            @PathVariable UUID id) {
        corridaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // 🔎 Consultas customizadas
    @GetMapping("/buscar/piloto")
    @Operation(summary = "Buscar corridas por piloto",
            description = "Retorna todas as corridas em que um piloto específico participou.")
    @ApiResponse(responseCode = "200", description = "Lista de corridas encontradas para o piloto")
    public ResponseEntity<List<CorridaResponseDTO>> findByPiloto(
            @Parameter(description = "Nome do piloto", required = true)
            @RequestParam String nome) {
        return ResponseEntity.ok(corridaService.findByPiloto(nome));
    }

    @GetMapping("/buscar/pais")
    @Operation(summary = "Buscar corridas por país",
            description = "Retorna todas as corridas realizadas em um país específico.")
    @ApiResponse(responseCode = "200", description = "Lista de corridas encontradas para o país")
    public ResponseEntity<List<CorridaResponseDTO>> findByPais(
            @Parameter(description = "Nome do país", required = true)
            @RequestParam String nome) {
        return ResponseEntity.ok(corridaService.findByPais(nome));
    }
}
