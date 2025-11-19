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
import senai.f1.dtos.request.PaisRequestDTO;
import senai.f1.dtos.response.PaisResponseDTO;
import senai.f1.service.PaisService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/paises")
@RequiredArgsConstructor
@Tag(name = "Países", description = "Operações relacionadas ao gerenciamento de países")
public class PaisController {

    private final PaisService paisService;

    @PostMapping
    @Operation(
            summary = "Criar novo país",
            description = "Cria um novo registro de país a partir dos dados fornecidos",
            responses = {
                    @ApiResponse(responseCode = "200", description = "País criado com sucesso",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PaisResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos",
                            content = @Content)
            }
    )
    public ResponseEntity<PaisResponseDTO> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados do país a ser criado",
                    required = true,
                    content = @Content(schema = @Schema(implementation = PaisRequestDTO.class))
            )
            @RequestBody @Valid PaisRequestDTO dto) {
        return ResponseEntity.ok(paisService.create(dto));
    }

    @GetMapping
    @Operation(
            summary = "Listar todos os países",
            description = "Retorna uma lista contendo todos os países cadastrados",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de países retornada com sucesso",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PaisResponseDTO.class)))
            }
    )
    public ResponseEntity<List<PaisResponseDTO>> listAll() {
        return ResponseEntity.ok(paisService.listAll());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar país por ID",
            description = "Retorna os dados de um país específico a partir do seu ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "País encontrado",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PaisResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "País não encontrado", content = @Content)
            }
    )
    public ResponseEntity<PaisResponseDTO> findById(
            @Parameter(description = "ID único do país", required = true)
            @PathVariable UUID id) {
        return ResponseEntity.ok(paisService.findById(id));
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar país",
            description = "Atualiza os dados de um país já existente",
            responses = {
                    @ApiResponse(responseCode = "200", description = "País atualizado com sucesso",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PaisResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos", content = @Content),
                    @ApiResponse(responseCode = "404", description = "País não encontrado", content = @Content)
            }
    )
    public ResponseEntity<PaisResponseDTO> update(
            @Parameter(description = "ID do país a ser atualizado", required = true)
            @PathVariable UUID id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Novos dados do país",
                    required = true,
                    content = @Content(schema = @Schema(implementation = PaisRequestDTO.class))
            )
            @RequestBody @Valid PaisRequestDTO dto) {
        return ResponseEntity.ok(paisService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Deletar país",
            description = "Remove permanentemente um país cadastrado",
            responses = {
                    @ApiResponse(responseCode = "204", description = "País removido com sucesso"),
                    @ApiResponse(responseCode = "404", description = "País não encontrado", content = @Content)
            }
    )
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID do país a ser removido", required = true)
            @PathVariable UUID id) {
        paisService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar")
    @Operation(
            summary = "Buscar país por nome",
            description = "Retorna os dados de um país a partir do nome informado",
            responses = {
                    @ApiResponse(responseCode = "200", description = "País encontrado",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PaisResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "País não encontrado", content = @Content)
            }
    )
    public ResponseEntity<PaisResponseDTO> findByNome(
            @Parameter(description = "Nome do país a ser buscado", required = true)
            @RequestParam String nome) {
        return ResponseEntity.ok(paisService.findByNome(nome));
    }
}
