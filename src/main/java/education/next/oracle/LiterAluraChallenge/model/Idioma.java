package education.next.oracle.LiterAluraChallenge.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "idiomas")
@Getter
@NoArgsConstructor
public class Idioma {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Linguagem linguagem;
    @ManyToOne
    @Setter
    private Livro livro;

    public Idioma(Linguagem linguagem) {
        this.linguagem = linguagem;
    }
}
