package senai.f1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import senai.f1.model.Pais;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaisRepository extends JpaRepository<Pais, UUID> {
    Optional<Pais> findByNomeIgnoreCase(String nome);
}