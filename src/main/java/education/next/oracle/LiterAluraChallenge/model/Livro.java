package education.next.oracle.LiterAluraChallenge.model;

import education.next.oracle.LiterAluraChallenge.dto.LivroDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
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
    private List<Autor> autores = new ArrayList<>();
    @OneToMany(mappedBy = "livro", cascade = CascadeType.ALL)
    private List<Idioma> idiomas = new ArrayList<>();
    private int numDownloads;

    public Livro (LivroDTO livroDTO) {
        this.titulo = livroDTO.titulo();
        livroDTO.autores().forEach(autorDTO -> {
            Autor autor = new Autor(autorDTO);
            autor.setLivro(this);
            this.autores.add(autor);
        });
        livroDTO.idiomas().forEach(idiomaDTO -> {
            Idioma idioma = new Idioma(Linguagem.converteParaLinguagem(idiomaDTO));
            idioma.setLivro(this);
            this.idiomas.add(idioma);
        });
        this.numDownloads = livroDTO.numDownloads();
    }
}
