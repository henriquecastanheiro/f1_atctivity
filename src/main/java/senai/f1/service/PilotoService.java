package senai.f1.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import senai.f1.dtos.request.PilotoRequestDTO;
import senai.f1.dtos.response.PilotoResponseDTO;
import senai.f1.mappers.PilotoMapper;
import senai.f1.model.Piloto;
import senai.f1.repository.PilotoRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PilotoService {
    private final PilotoRepository pilotoRepository;

    @Transactional
    public PilotoResponseDTO create(PilotoRequestDTO dto) {
        Piloto piloto = PilotoMapper.toEntity(dto);
        pilotoRepository.save(piloto);
        return PilotoMapper.toDTO(piloto);
    }

    public List<PilotoResponseDTO> listAll() {
        return pilotoRepository.findAll().stream().map(PilotoMapper::toDTO).toList();
    }

    public PilotoResponseDTO findById(UUID id) {
        return pilotoRepository.findById(id)
                .map(PilotoMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Piloto não encontrado"));
    }

    @Transactional
    public PilotoResponseDTO update(UUID id, PilotoRequestDTO dto) {
        Piloto piloto = pilotoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Piloto não encontrado"));
        piloto.setNome(dto.nome());
        piloto.setEquipe(dto.equipe());
        return PilotoMapper.toDTO(pilotoRepository.save(piloto));
    }

    @Transactional
    public void delete(UUID id) {
        if (!pilotoRepository.existsById(id)) {
            throw new RuntimeException("Piloto não encontrado");
        }
        pilotoRepository.deleteById(id);
    }

    // Consulta customizada
    public List<PilotoResponseDTO> findByEquipe(String equipe) {
        return pilotoRepository.findByEquipeIgnoreCase(equipe).stream()
                .map(PilotoMapper::toDTO).toList();
    }
}

