package senai.f1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import senai.f1.enums.Dificuldade;
import senai.f1.model.Pista;

import java.util.List;
import java.util.UUID;

@Repository
public interface PistaRepository extends JpaRepository<Pista, UUID> {
    List<Pista> findByPais_NomeIgnoreCase(String paisNome);
    List<Pista> findByDificuldade(Dificuldade dificuldade);
}
