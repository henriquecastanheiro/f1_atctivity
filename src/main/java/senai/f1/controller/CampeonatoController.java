package senai.f1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import senai.f1.dtos.request.CampeonatoRequestDTO;
import senai.f1.dtos.response.CampeonatoResponseDTO;
import senai.f1.service.CampeonatoService;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/campeonatos")
@RequiredArgsConstructor
@Tag(name = "Campeonatos", description = "Gerenciamento de campeonatos de Fórmula 1")
public class CampeonatoController {

    private final CampeonatoService campeonatoService;

    @PostMapping
    @Operation(
            summary = "Criar campeonato",
            description = "Cria um novo campeonato a partir dos dados fornecidos",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Campeonato criado com sucesso",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CampeonatoResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos", content = @Content)
            }
    )
    public ResponseEntity<CampeonatoResponseDTO> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados do campeonato a ser criado",
                    required = true,
                    content = @Content(schema = @Schema(implementation = CampeonatoRequestDTO.class))
            )
            @RequestBody @Valid CampeonatoRequestDTO dto) {
        return ResponseEntity.ok(campeonatoService.create(dto));
    }

    @GetMapping
    @Operation(
            summary = "Listar campeonatos",
            description = "Retorna todos os campeonatos cadastrados",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CampeonatoResponseDTO.class)))
            }
    )
    public ResponseEntity<List<CampeonatoResponseDTO>> listAll() {
        return ResponseEntity.ok(campeonatoService.listAll());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar campeonato por ID",
            description = "Retorna os dados de um campeonato específico",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Campeonato encontrado",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CampeonatoResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Campeonato não encontrado", content = @Content)
            }
    )
    public ResponseEntity<CampeonatoResponseDTO> findById(
            @Parameter(description = "ID único do campeonato", required = true)
            @PathVariable UUID id) {
        return ResponseEntity.ok(campeonatoService.findById(id));
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar campeonato",
            description = "Atualiza os dados de um campeonato existente",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Campeonato atualizado com sucesso",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CampeonatoResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Campeonato não encontrado", content = @Content)
            }
    )
    public ResponseEntity<CampeonatoResponseDTO> update(
            @Parameter(description = "ID do campeonato a ser atualizado", required = true)
            @PathVariable UUID id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Novos dados do campeonato",
                    required = true,
                    content = @Content(schema = @Schema(implementation = CampeonatoRequestDTO.class))
            )
            @RequestBody @Valid CampeonatoRequestDTO dto) {
        return ResponseEntity.ok(campeonatoService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Excluir campeonato",
            description = "Remove permanentemente um campeonato cadastrado",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Campeonato removido com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Campeonato não encontrado", content = @Content)
            }
    )
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID do campeonato a ser removido", required = true)
            @PathVariable UUID id) {
        campeonatoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar/equipe")
    @Operation(
            summary = "Buscar campeonatos por equipe",
            description = "Retorna todos os campeonatos em que uma determinada equipe participou",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de campeonatos encontrada",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CampeonatoResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Nenhum campeonato encontrado para a equipe informada", content = @Content)
            }
    )
    public ResponseEntity<List<CampeonatoResponseDTO>> findByEquipe(
            @Parameter(description = "Nome da equipe para filtrar campeonatos", required = true)
            @RequestParam String nome) {
        return ResponseEntity.ok(campeonatoService.findByEquipe(nome));
    }
}
