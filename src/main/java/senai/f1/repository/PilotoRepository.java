package senai.f1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import senai.f1.model.Piloto;

import java.util.List;
import java.util.UUID;

@Repository
public interface PilotoRepository extends JpaRepository<Piloto, UUID> {
    List<Piloto> findByEquipeIgnoreCase(String equipe);
}
