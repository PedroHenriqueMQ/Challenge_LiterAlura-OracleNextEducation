package education.next.oracle.LiterAluraChallenge.model;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "idiomas")
@Data
public class Idioma {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String linguagem;
    @ManyToOne
    private Livro livro;
}
