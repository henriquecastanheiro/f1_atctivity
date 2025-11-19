package senai.f1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import senai.f1.model.Corrida;

import java.util.List;
import java.util.UUID;

@Repository
public interface CorridaRepository extends JpaRepository<Corrida, UUID> {

    @Query("select c from Corrida c join c.podio p where p.nome = :nome")
    List<Corrida> findCorridasByPiloto(@Param("nome") String nome);

    List<Corrida> findByPista_Pais_Nome(String nomePais);
}
