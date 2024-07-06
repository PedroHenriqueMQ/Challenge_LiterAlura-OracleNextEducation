package education.next.oracle.LiterAluraChallenge.model;

import education.next.oracle.LiterAluraChallenge.dto.AutorDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "autores")
@Getter
@NoArgsConstructor
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String nome;
    private int anoNascimento;
    private int anoFalecimento;
    @ManyToOne
    private Livro livro;

    public Autor(AutorDTO autorDTO) {
        this.nome = autorDTO.nome();
        this.anoNascimento = autorDTO.anoNascimento();
        this.anoFalecimento = autorDTO.anoFalecimento();
    }
}
