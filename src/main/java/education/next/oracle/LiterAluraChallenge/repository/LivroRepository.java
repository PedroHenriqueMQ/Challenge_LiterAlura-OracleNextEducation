package education.next.oracle.LiterAluraChallenge.repository;

import education.next.oracle.LiterAluraChallenge.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivroRepository extends JpaRepository<Livro, Long> {
}
