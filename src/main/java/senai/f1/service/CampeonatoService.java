package senai.f1.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import senai.f1.dtos.request.CampeonatoRequestDTO;
import senai.f1.dtos.response.CampeonatoResponseDTO;
import senai.f1.mappers.CampeonatoMapper;
import senai.f1.model.Campeonato;
import senai.f1.model.Corrida;
import senai.f1.repository.CampeonatoRepository;
import senai.f1.repository.CorridaRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CampeonatoService {
    private final CampeonatoRepository campeonatoRepository;
    private final CorridaRepository corridaRepository;

    @Transactional
    public CampeonatoResponseDTO create(CampeonatoRequestDTO dto) {
        List<Corrida> corridas = corridaRepository.findAllById(dto.corridasIds());
        if (corridas.size() != dto.corridasIds().size()) {
            throw new RuntimeException("Uma ou mais corridas não encontradas");
        }
        Campeonato campeonato = CampeonatoMapper.toEntity(dto, corridas);
        campeonatoRepository.save(campeonato);
        return CampeonatoMapper.toDTO(campeonato);
    }

    public List<CampeonatoResponseDTO> listAll() {
        return campeonatoRepository.findAll().stream().map(CampeonatoMapper::toDTO).toList();
    }

    public CampeonatoResponseDTO findById(UUID id) {
        return campeonatoRepository.findById(id)
                .map(CampeonatoMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Campeonato não encontrado"));
    }

    @Transactional
    public CampeonatoResponseDTO update(UUID id, CampeonatoRequestDTO dto) {
        Campeonato campeonato = campeonatoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Campeonato não encontrado"));
        List<Corrida> corridas = corridaRepository.findAllById(dto.corridasIds());
        if (corridas.size() != dto.corridasIds().size()) {
            throw new RuntimeException("Uma ou mais corridas não encontradas");
        }
        campeonato.setCorridas(corridas);
        return CampeonatoMapper.toDTO(campeonatoRepository.save(campeonato));
    }

    @Transactional
    public void delete(UUID id) {
        if (!campeonatoRepository.existsById(id)) {
            throw new RuntimeException("Campeonato não encontrado");
        }
        campeonatoRepository.deleteById(id);
    }

    // Consulta customizada
    public List<CampeonatoResponseDTO> findByEquipe(String equipe) {
        return campeonatoRepository.findCampeonatosByEquipe(equipe).stream()
                .map(CampeonatoMapper::toDTO).toList();
    }
}
