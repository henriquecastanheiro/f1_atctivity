package senai.f1.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import senai.f1.model.Pais;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ActiveProfiles("test")
class PaisRepositoryTest {

    @Autowired
    private PaisRepository paisRepository;

    @Test
    void deveBuscarPorNome() {
        Optional<Pais> encontrado = paisRepository.findByNomeIgnoreCase("Brasil");
        assertTrue(encontrado.isPresent());
        assertEquals("Brasil", encontrado.get().getNome());
    }
}