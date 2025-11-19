package senai.f1.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import senai.f1.dtos.request.CorridaRequestDTO;
import senai.f1.dtos.response.CorridaResponseDTO;
import senai.f1.mappers.CorridaMapper;
import senai.f1.model.Corrida;
import senai.f1.model.Piloto;
import senai.f1.model.Pista;
import senai.f1.repository.CorridaRepository;
import senai.f1.repository.PilotoRepository;
import senai.f1.repository.PistaRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CorridaService {
    private final CorridaRepository corridaRepository;
    private final PistaRepository pistaRepository;
    private final PilotoRepository pilotoRepository;

    @Transactional
    public CorridaResponseDTO create(CorridaRequestDTO dto) {
        Pista pista = pistaRepository.findById(dto.pistaId())
                .orElseThrow(() -> new RuntimeException("Pista não encontrada"));
        List<Piloto> pilotos = pilotoRepository.findAllById(dto.podioIds());
        if (pilotos.size() != dto.podioIds().size()) {
            throw new RuntimeException("Um ou mais pilotos não encontrados");
        }
        Corrida corrida = CorridaMapper.toEntity(dto, pista, pilotos);
        corridaRepository.save(corrida);
        return CorridaMapper.toDTO(corrida);
    }

    public List<CorridaResponseDTO> listAll() {
        return corridaRepository.findAll().stream().map(CorridaMapper::toDTO).toList();
    }

    public CorridaResponseDTO findById(UUID id) {
        return corridaRepository.findById(id)
                .map(CorridaMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Corrida não encontrada"));
    }

    @Transactional
    public CorridaResponseDTO update(UUID id, CorridaRequestDTO dto) {
        Corrida corrida = corridaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Corrida não encontrada"));
        Pista pista = pistaRepository.findById(dto.pistaId())
                .orElseThrow(() -> new RuntimeException("Pista não encontrada"));
        List<Piloto> pilotos = pilotoRepository.findAllById(dto.podioIds());
        if (pilotos.size() != dto.podioIds().size()) {
            throw new RuntimeException("Um ou mais pilotos não encontrados");
        }
        corrida.setPista(pista);
        corrida.setPodio(pilotos);
        return CorridaMapper.toDTO(corridaRepository.save(corrida));
    }

    @Transactional
    public void delete(UUID id) {
        if (!corridaRepository.existsById(id)) {
            throw new RuntimeException("Corrida não encontrada");
        }
        corridaRepository.deleteById(id);
    }

    // Consultas customizadas
    public List<CorridaResponseDTO> findByPiloto(String nomePiloto) {
        return corridaRepository.findCorridasByPiloto(nomePiloto).stream()
                .map(CorridaMapper::toDTO).toList();
    }

    public List<CorridaResponseDTO> findByPais(String paisNome) {
        return corridaRepository.findByPista_Pais_Nome(paisNome).stream()
                .map(CorridaMapper::toDTO).toList();
    }
}

