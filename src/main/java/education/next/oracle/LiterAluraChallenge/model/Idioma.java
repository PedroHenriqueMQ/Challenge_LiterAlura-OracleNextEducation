package education.next.oracle.LiterAluraChallenge.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "idiomas")
@Getter
@NoArgsConstructor
public class Idioma {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String linguagem;
    @ManyToOne
    private Livro livro;

    public Idioma(String idioma) {
        this.linguagem = idioma;
    }
}
