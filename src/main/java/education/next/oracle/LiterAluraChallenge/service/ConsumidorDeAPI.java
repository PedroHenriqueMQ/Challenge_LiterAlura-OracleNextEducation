package education.next.oracle.LiterAluraChallenge.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import education.next.oracle.LiterAluraChallenge.dto.LivroDTO;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsumidorDeAPI {
    public static LivroDTO fazerRequest(String endereco) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                                         .uri(URI.create(endereco))
                                         .build();
        HttpResponse<String> response = null;

        try {
            response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        return conversorLivro(response.body());
    }

    private static LivroDTO conversorLivro (String json) {
        try {
            return new ObjectMapper().readValue(json, LivroDTO.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erro ao converter o objeto de resposta para Livro: " + e.getMessage());
        }
    }
}
