package education.next.oracle.LiterAluraChallenge.menu;

import education.next.oracle.LiterAluraChallenge.dto.LivroDTO;
import education.next.oracle.LiterAluraChallenge.service.ConsumidorDeAPI;

import java.util.*;
import java.util.stream.Collectors;

public class Menu {
    private static int proxEscolha () {
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

    private static void printador (String titulo, String[] opcoes) {
        System.out.println(titulo + "\n");

        for (int i = 0; i < opcoes.length; i++) {
            System.out.println(i+1 + " - " + opcoes[i]);
        }

        System.out.println("\nDigite um número para continuar:");
    }

    public static void iniciar() {
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

    private static boolean selecionaIdiomaConsulta() {
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
            default -> {
                System.out.println("Digite um idioma válido.");

                try {
                    Thread.sleep(1500); // Espera 1 segundo e meio
                } catch (InterruptedException e) {
                    System.out.println("Erro ao por a Thread para dormir: " + e.getMessage());
                }

                yield true;
            }
        };
    }

    private static boolean consultarLivrosInternet(String idioma) {
        String enderecoDeBuscaPadrao = "https://gutendex.com/books/";

        printador (
                 new String("Que tipo de consulta você deseja fazer? (Idioma atual: %s)").formatted(idioma),
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

                printarBuscaApi(enderecoBusca, idioma);

                yield false;
            }
            case 2 -> {
                printarBuscaApi(enderecoDeBuscaPadrao, idioma);

                yield false;
            }
            default -> throw new IllegalStateException("Unexpected value: " + proxEscolha());
        };
    }

    private static void printarBuscaApi(String enderecoBusca, String idioma) {
        boolean buscaGeral = enderecoBusca.endsWith("/books/");

        String enderecoAtualizado = buscaGeral ?
                enderecoBusca + String.format("?languages=%s", idioma) :
                enderecoBusca;

        List<LivroDTO> consulta = ConsumidorDeAPI.fazerRequest(enderecoAtualizado);
        List<LivroDTO> itensDaConsultaFiltrados;

        if (buscaGeral) itensDaConsultaFiltrados = new ArrayList<>(consulta);
        else itensDaConsultaFiltrados = consulta.stream().filter(c -> c.idiomas().contains(idioma)).toList();

        for (int i = 0; i < itensDaConsultaFiltrados.size(); i++) {
            System.out.print("_______________________________________________________\n" +
                    "Livro " + (i+1) + ":\n" + itensDaConsultaFiltrados.get(i).toString());
        }
    }
}
