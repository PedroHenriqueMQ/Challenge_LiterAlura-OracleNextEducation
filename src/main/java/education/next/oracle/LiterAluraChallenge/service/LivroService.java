package education.next.oracle.LiterAluraChallenge.service;

import education.next.oracle.LiterAluraChallenge.model.Livro;
import education.next.oracle.LiterAluraChallenge.repository.LivroRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LivroService {
    LivroRepository livroRepository;

    public void salvarLivro (Livro livro) {
        livroRepository.save(livro);
    }
}
