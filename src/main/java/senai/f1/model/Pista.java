package senai.f1.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import senai.f1.enums.Dificuldade;

import java.util.UUID;

@Entity
@Getter
@Setter
public class Pista {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    private Pais pais;

    private Double distancia;

    @Enumerated(EnumType.STRING)
    private Dificuldade dificuldade;

}
