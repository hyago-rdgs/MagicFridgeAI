package br.com.hyagorodrigues.MagicFridgeAI.service;

import br.com.hyagorodrigues.MagicFridgeAI.dto.FoodItemDTO;
import br.com.hyagorodrigues.MagicFridgeAI.model.FoodItem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GeminiService {

    public final WebClient webClient;
    private String geminiApiKey = System.getenv("GEMINI_API_KEY");

    public GeminiService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<String> generateRecipe(List<FoodItemDTO> foodItemDTOList) {
        String itens = foodItemDTOList.stream()
                .map(foodItemDTO -> String.format(
                        "%s (%s) - Quantidade: %d, Validade: %s;",
                        foodItemDTO.getNome(), foodItemDTO.getCategoria(), foodItemDTO.getQuantidade(), foodItemDTO.getValidade()))
                .collect(Collectors.joining("\n"));

        String prompt = "Baseado no meu bando de dados faca uma receita com os seguintes itens: \n" + itens;

        Map<String, Object> requestBody = Map.of(
                "model", "gemini-3.6-flash",
                "system_instruction", "Voce é um assistente que cria receitas.",
                "input", prompt
        );

        return webClient.post()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header("x-goog-api-key", geminiApiKey)
                .header("Api-Revision", "2026-05-20")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> {
                    var steps = (List<Map<String, Object>>) response.get("steps");

                    if (steps != null && !steps.isEmpty()) {

                        return steps.stream()
                                .filter(step -> "model_output".equals(step.get("type")))
                                .findFirst()
                                .map(step -> (List<Map<String, Object>>) step.get("content"))
                                .filter(contents -> contents != null && !contents.isEmpty())
                                .map(contents -> contents.get(0).get("text"))
                                .map(Object::toString)
                                .orElse("Nenhuma receita foi gerada!");
                    }

                    return "Nenhuma receita foi gerada!";
                });
    }
}
