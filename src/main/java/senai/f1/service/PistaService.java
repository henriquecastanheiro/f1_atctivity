package senai.f1.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import senai.f1.dtos.request.PistaRequestDTO;
import senai.f1.dtos.response.PistaResponseDTO;
import senai.f1.enums.Dificuldade;
import senai.f1.mappers.PistaMapper;
import senai.f1.model.Pais;
import senai.f1.model.Pista;
import senai.f1.repository.PaisRepository;
import senai.f1.repository.PistaRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PistaService {
    private final PistaRepository pistaRepository;
    private final PaisRepository paisRepository;

    @Transactional
    public PistaResponseDTO create(PistaRequestDTO dto) {
        Pais pais = paisRepository.findById(dto.paisId())
                .orElseThrow(() -> new RuntimeException("Pais não encontrado"));
        Pista pista = PistaMapper.toEntity(dto, pais);
        pistaRepository.save(pista);
        return PistaMapper.toDTO(pista);
    }

    public List<PistaResponseDTO> listAll() {
        return pistaRepository.findAll().stream().map(PistaMapper::toDTO).toList();
    }

    public PistaResponseDTO findById(UUID id) {
        return pistaRepository.findById(id)
                .map(PistaMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Pista não encontrada"));
    }

    @Transactional
    public PistaResponseDTO update(UUID id, PistaRequestDTO dto) {
        Pista pista = pistaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pista não encontrada"));
        Pais pais = paisRepository.findById(dto.paisId())
                .orElseThrow(() -> new RuntimeException("Pais não encontrado"));

        pista.setPais(pais);
        pista.setDistancia(dto.distancia());
        pista.setDificuldade(Dificuldade.valueOf(dto.dificuldade()));

        return PistaMapper.toDTO(pistaRepository.save(pista));
    }

    @Transactional
    public void delete(UUID id) {
        if (!pistaRepository.existsById(id)) {
            throw new RuntimeException("Pista não encontrada");
        }
        pistaRepository.deleteById(id);
    }

    // Consultas customizadas
    public List<PistaResponseDTO> findByPais(String paisNome) {
        return pistaRepository.findByPais_NomeIgnoreCase(paisNome).stream()
                .map(PistaMapper::toDTO).toList();
    }

    public List<PistaResponseDTO> findByDificuldade(Dificuldade dificuldade) {
        return pistaRepository.findByDificuldade(dificuldade).stream()
                .map(PistaMapper::toDTO).toList();
    }
}

