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
import senai.f1.dtos.request.PilotoRequestDTO;
import senai.f1.dtos.response.PilotoResponseDTO;
import senai.f1.service.PilotoService;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/pilotos")
@RequiredArgsConstructor
@Tag(name = "Pilotos", description = "Gerenciamento de pilotos de Fórmula 1")
public class PilotoController {
    private final PilotoService pilotoService;

    @PostMapping
    @Operation(
            summary = "Cadastrar piloto",
            description = "Cria um novo piloto no sistema a partir dos dados fornecidos."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Piloto cadastrado com sucesso",
                    content = @Content(schema = @Schema(implementation = PilotoResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos enviados na requisição")
    })
    public ResponseEntity<PilotoResponseDTO> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados necessários para cadastrar um novo piloto",
                    required = true
            )
            @RequestBody @Valid PilotoRequestDTO dto) {
        return ResponseEntity.ok(pilotoService.create(dto));
    }

    @GetMapping
    @Operation(summary = "Listar todos os pilotos",
            description = "Retorna a lista completa de pilotos cadastrados.")
    @ApiResponse(responseCode = "200", description = "Lista de pilotos retornada com sucesso")
    public ResponseEntity<List<PilotoResponseDTO>> listAll() {
        return ResponseEntity.ok(pilotoService.listAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar piloto por ID",
            description = "Retorna os dados de um piloto específico pelo seu identificador único (UUID).")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Piloto encontrado",
                    content = @Content(schema = @Schema(implementation = PilotoResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Piloto não encontrado")
    })
    public ResponseEntity<PilotoResponseDTO> findById(
            @Parameter(description = "ID único do piloto", required = true)
            @PathVariable UUID id) {
        return ResponseEntity.ok(pilotoService.findById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar piloto",
            description = "Atualiza os dados de um piloto existente pelo seu ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Piloto atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para atualização"),
            @ApiResponse(responseCode = "404", description = "Piloto não encontrado")
    })
    public ResponseEntity<PilotoResponseDTO> update(
            @Parameter(description = "ID do piloto a ser atualizado", required = true)
            @PathVariable UUID id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Novos dados do piloto",
                    required = true
            )
            @RequestBody @Valid PilotoRequestDTO dto) {
        return ResponseEntity.ok(pilotoService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir piloto",
            description = "Remove um piloto do sistema com base no seu ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Piloto removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Piloto não encontrado")
    })
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID do piloto a ser excluído", required = true)
            @PathVariable UUID id) {
        pilotoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // 🔎 Consulta customizada
    @GetMapping("/buscar/equipe")
    @Operation(summary = "Buscar pilotos por equipe",
            description = "Retorna todos os pilotos que pertencem a uma determinada equipe.")
    @ApiResponse(responseCode = "200", description = "Lista de pilotos encontrada para a equipe")
    public ResponseEntity<List<PilotoResponseDTO>> findByEquipe(
            @Parameter(description = "Nome da equipe", required = true)
            @RequestParam String nome) {
        return ResponseEntity.ok(pilotoService.findByEquipe(nome));
    }
}
