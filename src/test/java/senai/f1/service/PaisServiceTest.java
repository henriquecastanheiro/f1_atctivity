package senai.f1.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import senai.f1.dtos.request.PaisRequestDTO;
import senai.f1.dtos.response.PaisResponseDTO;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class PaisServiceTest {

    @Autowired
    private PaisService service;

    @Test
    void create() {
        PaisRequestDTO paisRequestDTO = new PaisRequestDTO("Portugal");
        PaisResponseDTO response = service.create(paisRequestDTO);
        assertNotNull(response);
        assertNotNull(response.id());
        assertEquals("Portugal", response.nome());
    }
}