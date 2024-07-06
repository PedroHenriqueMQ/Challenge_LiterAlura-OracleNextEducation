package education.next.oracle.LiterAluraChallenge.menu;

import education.next.oracle.LiterAluraChallenge.dto.LivroDTO;
import education.next.oracle.LiterAluraChallenge.model.Livro;
import education.next.oracle.LiterAluraChallenge.service.ConsumidorDeAPI;
import education.next.oracle.LiterAluraChallenge.service.LivroService;
import lombok.AllArgsConstructor;

import java.util.*;

@AllArgsConstructor
public class Menu {
    LivroService livroService;

    private int proxEscolha () {
        try {
            return new Scanner(System.in).nextInt();
        } catch (InputMismatchException e) {
            System.err.println("Erro na sua escolha " + e.getMessage());
            System.out.println("Lembre-se de digitar apenas números");

            try {
                Thread.sleep(2000); // Espera 2 segundos
            } catch (InterruptedException err) {
                System.out.println("Erro ao por a Thread para dormir: " + err.getMessage());
            }
        }

        return 0;
    }

    private boolean erroEscolhaDeOpcao () {
        System.err.println("Digite um número que esteja na lista de seleção.");

        try {
            Thread.sleep(1500); // Espera 1 segundo e meio
        } catch (InterruptedException e) {
            System.err.println("Erro ao por a Thread para dormir: " + e.getMessage());
        }

        return true;
    }

    private void printador (String titulo, String[] opcoes) {
        System.out.println(titulo + "\n");

        for (int i = 0; i < opcoes.length; i++) {
            System.out.println(i+1 + " - " + opcoes[i]);
        }

        System.out.println("\nDigite um número para continuar:");
    }

    public void iniciar() {
        boolean condicaoDeRepeticao = true;

        while (condicaoDeRepeticao) {
            System.out.println(
                    """
                    ***********************************
                    ===================================
                    Seja muito bem-vindo ao LiterAlura!
                    ===================================
                    ***********************************
                    """);

            printador(
                    "O que você deseja fazer?",
                    new String[]{
                            "Consultar livros da internet"
                    }
            );

            switch (proxEscolha()) {
                case 1:
                    while (selecionaIdiomaConsulta());
                    break;
                case 2:
                    condicaoDeRepeticao = false;
            }
        }
    }

    private boolean selecionaIdiomaConsulta() {
        printador (
                "Qual idioma você prefere para consulta?",
                new String[]{
                        "Todos idiomas",
                        "Inglês",
                        "Português",
                        "Espanhol",
                        "Voltar"
                }
        );

        return switch (proxEscolha()) {
            case 1 -> consultarLivrosInternet("todos");
            case 2 -> consultarLivrosInternet("en");
            case 3 -> consultarLivrosInternet("pt");
            case 4 -> consultarLivrosInternet("es");
            case 5 -> false;
            default -> erroEscolhaDeOpcao();
        };
    }

    private boolean consultarLivrosInternet(String idioma) {
        String enderecoDeBuscaPadrao = "https://gutendex.com/books/";

        printador (
                 ("Que tipo de consulta você deseja fazer? (Idioma atual: %s)").formatted(idioma),
                 new String[]{
                         "Pesquisar por título",
                         "Listar todos os livros",
                         "Voltar à página anterior",
                         "Voltar ao início"
                 }
        );

        return switch (proxEscolha()) {
            case 1 -> {
                System.out.print("Digite o título do livro que deseja pesquisar: ");
                String pesquisa = new Scanner(System.in).nextLine();
                String enderecoBusca = String.format(enderecoDeBuscaPadrao + "?search=%s", pesquisa);

                List<LivroDTO> busca = buscaApi(enderecoBusca, idioma);
                printarBuscaApi(busca);
                while (persistirLivros(busca));

                yield false;
            }
            case 2 -> {
                List<LivroDTO> busca = buscaApi(enderecoDeBuscaPadrao, idioma);
                printarBuscaApi(busca);
                while (persistirLivros(busca));

                yield false;
            }
            case 3 -> true;
            case 4 -> false;
            default -> erroEscolhaDeOpcao();
        };
    }

    private List<LivroDTO> buscaApi (String enderecoBusca, String idioma) {
        boolean buscaGeral = enderecoBusca.endsWith("/books/");

        String enderecoAtualizado = buscaGeral ?
                enderecoBusca :
                enderecoBusca + String.format("?languages=%s", idioma);

        List<LivroDTO> consulta = ConsumidorDeAPI.fazerRequest(enderecoAtualizado);
        List<LivroDTO> itensDaConsultaFiltrados;

        if (buscaGeral) itensDaConsultaFiltrados = new ArrayList<>(consulta);
        else itensDaConsultaFiltrados = consulta.stream().filter(c -> c.idiomas().contains(idioma)).toList();

        System.out.println(itensDaConsultaFiltrados);

        return itensDaConsultaFiltrados;
    }

    private void printarBuscaApi(List<LivroDTO> itensDaConsultaFiltrados) {
        for (int i = 0; i < itensDaConsultaFiltrados.size(); i++) {
            System.out.print("_______________________________________________________\n" +
                    "Livro " + (i+1) + ":\n" + itensDaConsultaFiltrados.get(i).toString());
        }
    }

    private boolean persistirLivros (List<LivroDTO> livrosDTO) {
        printador(
                "Deseja salvar algum(ns) livro(s)?",
                new String[]{"Sim", "Não"}
        );

        return switch (proxEscolha()) {
            case 1 -> escolhaDePersistenciaDosLivros(livrosDTO);
            case 2 -> false;
            default -> erroEscolhaDeOpcao();
        };
    }

    private boolean escolhaDePersistenciaDosLivros (List<LivroDTO> livrosDTO) {
        printador(
                "Deseja salvar quantos livros?",
                new String[]{
                        "Apenas um",
                        "Uma quantia específica",
                        "Todos",
                        "Cancelar"
                }
        );

        return switch (proxEscolha()) {
            case 1 -> salvarIntervalo(livrosDTO, new int[1]);
            case 2 -> salvarIntervalo(livrosDTO, new int[2]);
            case 3 -> salvarTodos(livrosDTO);
            case 4 -> true;
            default -> erroEscolhaDeOpcao();
        };
    }

    private boolean salvarIntervalo (List<LivroDTO> livrosDTO, int[] intervalo) {
        for (int i = 0; i < intervalo.length; i++) {
            if (intervalo.length > 1)
                System.out.println("Digite dois números correspondentes ao intevalo dos livros que deseja salvar " +
                        "(Ex.: do primeiro ao último): ");
            else System.out.println("Digite o número que corresponde ao livro que deseja salvar: ");
            intervalo[i] = proxEscolha();
        }

        if (intervalo[0] <= 0 && intervalo[1] <= 0) return erroEscolhaDeOpcao();
        else if (intervalo[0] > livrosDTO.size() || intervalo[1] > livrosDTO.size()) return erroEscolhaDeOpcao();

        int maiorValor = Math.max(intervalo[0], intervalo[1]);
        if (intervalo[0] <= 0 || intervalo[1] <= 0) {
            livroService.salvarLivro(
                    new Livro(livrosDTO.get(maiorValor))
            );

            return false;
        }

        int menorValor = Math.min(intervalo[0], intervalo[1]);
        livrosDTO.subList(menorValor-1, maiorValor).forEach(
                livroDTO -> livroService.salvarLivro(new Livro(livroDTO))
        );

        return false;
    }

    private boolean salvarTodos(List<LivroDTO> livrosDTO) {
        livrosDTO.forEach(
                livroDTO -> livroService.salvarLivro(new Livro(livroDTO))
        );

        return false;
    }
}
