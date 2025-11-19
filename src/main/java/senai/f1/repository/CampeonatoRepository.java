package senai.f1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import senai.f1.model.Campeonato;

import java.util.List;
import java.util.UUID;

@Repository
public interface CampeonatoRepository extends JpaRepository<Campeonato, UUID> {
    @Query("select c from Campeonato c join c.corridas r join r.podio p where p.equipe = :equipe")
    List<Campeonato> findCampeonatosByEquipe(@Param("equipe") String equipe);
}