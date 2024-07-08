package education.next.oracle.LiterAluraChallenge.service;

import education.next.oracle.LiterAluraChallenge.model.Linguagem;
import education.next.oracle.LiterAluraChallenge.model.Livro;
import education.next.oracle.LiterAluraChallenge.repository.LivroRepository;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class LivroService {
    LivroRepository livroRepository;

    public void salvarLivro (Livro livro) {
        livroRepository.save(livro);
    }

    public List<Livro> encontrarLivrosPorLinguagem (Linguagem linguagem) {
        return livroRepository.findLivrosByIdiomasLinguagem(linguagem);
    }

    public List<Livro> encontrarLivrosPeloAnoDeFalescimentoDosAutores (int anoReferencia) {
        return livroRepository.findLivrosByAutoresAnoFalecimentoGreaterThan(anoReferencia);
    }

    public List<Livro> encontrarTodosOsLivros () {
        return livroRepository.findAll();
    }
}
