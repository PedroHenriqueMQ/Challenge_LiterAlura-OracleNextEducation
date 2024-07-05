package education.next.oracle.LiterAluraChallenge.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ResponseDTO (
        @JsonAlias("results") List<LivroDTO> livrosEncontrados
) {}
