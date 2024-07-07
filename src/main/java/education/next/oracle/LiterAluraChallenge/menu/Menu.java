package education.next.oracle.LiterAluraChallenge.menu;

import education.next.oracle.LiterAluraChallenge.dto.LivroDTO;
import education.next.oracle.LiterAluraChallenge.model.Linguagem;
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
        System.err.println("Digite uma opção válida.");

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
            case 1 -> consultarLivrosInternet(Linguagem.TODOS);
            case 2 -> consultarLivrosInternet(Linguagem.INGLES);
            case 3 -> consultarLivrosInternet(Linguagem.PORTUGUES);
            case 4 -> consultarLivrosInternet(Linguagem.ESPANHOL);
            case 5 -> false;
            default -> erroEscolhaDeOpcao();
        };
    }

    private boolean consultarLivrosInternet(Linguagem linguagem) {
        String enderecoDeBuscaPadrao = "https://gutendex.com/books/";

        printador (
                 ("Que tipo de consulta você deseja fazer? (Idioma atual: %s)").formatted(linguagem.getIdioma()),
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

                List<LivroDTO> busca = buscaApi(enderecoBusca, linguagem);
                if (printarBuscaApi(busca)) while (persistirLivros(busca));

                yield false;
            }
            case 2 -> {
                List<LivroDTO> busca = buscaApi(enderecoDeBuscaPadrao, linguagem);
                if (printarBuscaApi(busca)) while (persistirLivros(busca));

                yield false;
            }
            case 3 -> true;
            case 4 -> false;
            default -> erroEscolhaDeOpcao();
        };
    }

    private List<LivroDTO> buscaApi (String enderecoBusca, Linguagem linguagem) {
        boolean buscaGeral = enderecoBusca.endsWith("/books/");

        String enderecoAtualizado = buscaGeral && !linguagem.getIdioma().equals("todos") ?
                enderecoBusca + String.format("?languages=%s", linguagem.getIdioma()) :
                enderecoBusca;

        List<LivroDTO> consulta = ConsumidorDeAPI.fazerRequest(enderecoAtualizado);
        List<LivroDTO> itensDaConsultaFiltrados;

        if (linguagem.getIdioma().equals("todos")) itensDaConsultaFiltrados = new ArrayList<>(consulta);
        else itensDaConsultaFiltrados = consulta.stream().filter(
                livroDTO -> livroDTO.idiomas().contains(linguagem.getIdioma())
                ).toList();

        return itensDaConsultaFiltrados;
    }

    private boolean printarBuscaApi(List<LivroDTO> itensDaConsultaFiltrados) {
        for (int i = 0; i < itensDaConsultaFiltrados.size(); i++) {
            System.out.print("_______________________________________________________\n" +
                    "Livro " + (i+1) + ":\n" + itensDaConsultaFiltrados.get(i).toString());
        }

        if (itensDaConsultaFiltrados.isEmpty()) {
            System.err.println("Nenhum livro encontrado!");
            return false;
        } else return true;
    }

    private boolean persistirLivros (List<LivroDTO> livrosDTO) {
        printador(
                "O que você deseja fazer com o resultado de sua pesquisa?",
                new String[]{
                        "Lista de autores",
                        "Lista de autores vivos",
                        "Salvar offline",
                        "Concluído"
                }
        );

        return switch (proxEscolha()) {
            case 1 -> listarAutores(livrosDTO);
            case 2 -> listarAutoresVivos(livrosDTO);
            case 3 -> escolhaDePersistenciaDosLivros(livrosDTO);
            case 4 -> false;
            default -> erroEscolhaDeOpcao();
        };
    }

    private boolean listarAutores(List<LivroDTO> livrosDTO) {
        List<String> nomesAutores = livrosDTO.stream()
                .flatMap(livroDTO -> livroDTO.autores().stream())
                .map(autorDTO -> {
                    String[] nomeSplit = autorDTO.nome().split(",");
                    return Arrays.stream(nomeSplit).reduce((a, b) -> b + " " + a).orElse("");
                })
                .distinct()
                .toList();

        System.out.println("Lista de autores: ");
        for (int i = 0; i < nomesAutores.size(); i++) {
            System.out.println("Autor %d -> %s".formatted(i+1, nomesAutores.get(i)));
        }

        return true;
    }

    private boolean listarAutoresVivos(List<LivroDTO> livrosDTO) {
        System.out.print("Digite um ano de referência: ");
        int ano = proxEscolha();

        List<String> nomesAutores = livrosDTO.stream()
                .flatMap(livroDTO -> livroDTO.autores().stream())
                .filter(autorDTO -> autorDTO.anoFalecimento() > ano)
                .map(autorDTO -> {
                    String[] nomeSplit = autorDTO.nome().split(",");
                    return Arrays.stream(nomeSplit).reduce((a, b) -> b + " " + a).orElse("");
                })
                .distinct()
                .toList();

        System.out.printf("Lista de autores vivos até o ano de %d: %n", ano);
        for (int i = 0; i < nomesAutores.size(); i++) {
            System.out.println("Autor %d -> %s".formatted(i+1, nomesAutores.get(i)));
        }

        return true;
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
            case 1 -> salvarIntervalo(livrosDTO, new int[]{0});
            case 2 -> salvarIntervalo(livrosDTO, new int[]{0, 0});
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

        for (int valor : intervalo) {
            if (valor <= 0) return erroEscolhaDeOpcao();
            else if (valor > livrosDTO.size()) return erroEscolhaDeOpcao();
        }

        if (intervalo.length == 1) {
            livroService.salvarLivro(
                    new Livro(livrosDTO.get(0))
            );
        } else {
            int maiorValor = Arrays.stream(intervalo).max().getAsInt();
            int menorValor = Arrays.stream(intervalo).min().getAsInt();
            livrosDTO.subList(menorValor-1, maiorValor).forEach(
                    livroDTO -> livroService.salvarLivro(new Livro(livroDTO))
            );
        }

        return true;
    }

    private boolean salvarTodos(List<LivroDTO> livrosDTO) {
        livrosDTO.forEach(
                livroDTO -> livroService.salvarLivro(new Livro(livroDTO))
        );

        return true;
    }
}
