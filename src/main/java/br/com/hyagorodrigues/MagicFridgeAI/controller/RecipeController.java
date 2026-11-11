package br.com.hyagorodrigues.MagicFridgeAI.controller;

import br.com.hyagorodrigues.MagicFridgeAI.dto.FoodItemDTO;
import br.com.hyagorodrigues.MagicFridgeAI.model.FoodItem;
import br.com.hyagorodrigues.MagicFridgeAI.service.FoodItemService;
import br.com.hyagorodrigues.MagicFridgeAI.service.GeminiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/recipe")
public class RecipeController {

    private final FoodItemService foodItemService;
    private final GeminiService geminiService;

    public RecipeController(FoodItemService foodItemService, GeminiService geminiService) {
        this.foodItemService = foodItemService;
        this.geminiService = geminiService;
    }

    @GetMapping("/generate")
    public Mono<ResponseEntity<String>> generateRecipe() {
        List<FoodItemDTO> foodItemDTOList = foodItemService.listar();

        return geminiService.generateRecipe(foodItemDTOList)
                .map(recipe -> ResponseEntity.ok(recipe))
                .defaultIfEmpty(ResponseEntity.noContent().build());
    }
}
