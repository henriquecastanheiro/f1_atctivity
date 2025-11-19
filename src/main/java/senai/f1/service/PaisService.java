package senai.f1.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import senai.f1.dtos.request.PaisRequestDTO;
import senai.f1.dtos.response.PaisResponseDTO;
import senai.f1.mappers.PaisMapper;
import senai.f1.model.Pais;
import senai.f1.repository.PaisRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaisService {
    private final PaisRepository paisRepository;

    @Transactional
    public PaisResponseDTO create(PaisRequestDTO dto) {
        Pais pais = PaisMapper.toEntity(dto);
        paisRepository.save(pais);
        return PaisMapper.toDTO(pais);
    }

    public List<PaisResponseDTO> listAll() {
        return paisRepository.findAll().stream().map(PaisMapper::toDTO).toList();
    }

    public PaisResponseDTO findById(UUID id) {
        return paisRepository.findById(id)
                .map(PaisMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Pais não encontrado"));
    }

    @Transactional
    public PaisResponseDTO update(UUID id, PaisRequestDTO dto) {
        Pais pais = paisRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pais não encontrado"));
        pais.setNome(dto.nome());
        return PaisMapper.toDTO(paisRepository.save(pais));
    }

    @Transactional
    public void delete(UUID id) {
        if (!paisRepository.existsById(id)) {
            throw new RuntimeException("Pais não encontrado");
        }
        paisRepository.deleteById(id);
    }

    // Consulta customizada
    public PaisResponseDTO findByNome(String nome) {
        return paisRepository.findByNomeIgnoreCase(nome)
                .map(PaisMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Pais não encontrado"));
    }
}

