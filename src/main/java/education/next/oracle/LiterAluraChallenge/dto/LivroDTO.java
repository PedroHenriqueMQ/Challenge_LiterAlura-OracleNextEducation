package education.next.oracle.LiterAluraChallenge.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record LivroDTO(
        @JsonAlias("title") String titulo,
        @JsonAlias("authors") List<AutorDTO> autores,
        @JsonAlias("languages") List<String> idiomas,
        @JsonAlias("download_count") int numDownloads
) {
    @Override
    public String toString() {
        return String.format(
                        """
                        Livro: %s
                        Autor(es): %s
                        """,
                titulo,
                autores.size() > 1 ?
                        (autores + "...").replace("[","").replace("]","") :
                        autores.toString().replace("[","").replace("]","")
        );
    }
}
