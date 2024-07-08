package education.next.oracle.LiterAluraChallenge.repository;

import education.next.oracle.LiterAluraChallenge.model.Linguagem;
import education.next.oracle.LiterAluraChallenge.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long> {
    List<Livro> findLivrosByIdiomasLinguagem(Linguagem linguagem);
}
