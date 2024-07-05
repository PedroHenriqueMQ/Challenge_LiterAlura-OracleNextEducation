package education.next.oracle.LiterAluraChallenge.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "livros")
@Data
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
}
