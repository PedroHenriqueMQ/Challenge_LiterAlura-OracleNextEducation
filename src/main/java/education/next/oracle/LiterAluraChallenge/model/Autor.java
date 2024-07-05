package education.next.oracle.LiterAluraChallenge.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "autores")
@Data
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String nome;
    private int anoNascimento;
    private int anoFalecimento;
    @ManyToOne
    private Livro livro;
}
