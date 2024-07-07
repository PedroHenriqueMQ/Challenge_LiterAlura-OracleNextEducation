package education.next.oracle.LiterAluraChallenge.model;

import lombok.Getter;

@Getter
public enum Linguagem {
    TODOS("todos"),
    INGLES("en"),
    PORTUGUES("pt"),
    ESPANHOL("es");

    private String idioma;

    Linguagem (String idioma) {
        this.idioma = converterIdioma(idioma);
    }
    
    private String converterIdioma(String idioma) {
        return switch (idioma) {
            case "Inglês" -> "en";
            case "Português" -> "pt";
            case "Espanhol"-> "es";
            default -> "todos";
        };
    }

    public static Linguagem converteParaLinguagem (String idioma) {
        return switch (idioma) {
            case "en" -> Linguagem.INGLES;
            case "pt" -> Linguagem.PORTUGUES;
            case "es" -> Linguagem.ESPANHOL;
            default -> Linguagem.TODOS;
        };
    }
}
