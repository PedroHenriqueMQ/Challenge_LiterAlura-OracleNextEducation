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
    @OneToMany(mappedBy = "livro", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Autor> autores = new ArrayList<>();
    @OneToMany(mappedBy = "livro", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
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

    @Override
    public String toString() {
        List<String> autoresNomes = new ArrayList<>();
        autores.forEach(autor -> autoresNomes.add(autor.getNome()));

        return String.format(
                """
                Livro: %s
                Autor(es): %s
                """,
                titulo,
                autoresNomes.size() > 1 ?
                        (autoresNomes + "...").replace("[","").replace("]","") :
                        autoresNomes.toString().replace("[","").replace("]","")
        );
    }
}
