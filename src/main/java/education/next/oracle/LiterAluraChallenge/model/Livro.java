package education.next.oracle.LiterAluraChallenge.model;

import education.next.oracle.LiterAluraChallenge.dto.LivroDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "livros")
@Getter
@NoArgsConstructor
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    @OneToMany(mappedBy = "livro", cascade = CascadeType.ALL)
    private List<Autor> autores;
    @OneToMany(mappedBy = "livro", cascade = CascadeType.ALL)
    private List<Idioma> idiomas;
    private int numDownloads;

    public Livro (LivroDTO livroDTO) {
        this.titulo = livroDTO.titulo();
        livroDTO.autores().forEach(autorDTO -> this.autores.add(new Autor(autorDTO)));
        livroDTO.idiomas().forEach(idioma -> this.idiomas.add(new Idioma(idioma)));
        this.numDownloads = livroDTO.numDownloads();
    }
}
