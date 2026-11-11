package br.com.hyagorodrigues.MagicFridgeAI.mapper;

import br.com.hyagorodrigues.MagicFridgeAI.dto.FoodItemDTO;
import br.com.hyagorodrigues.MagicFridgeAI.model.FoodItem;
import org.springframework.stereotype.Component;

@Component
public class FoodItemMapper {

    public FoodItemDTO map(FoodItem foodItem) {
        FoodItemDTO foodItemDTO = new FoodItemDTO();

        foodItemDTO.setId(foodItem.getId());
        foodItemDTO.setNome(foodItem.getNome());
        foodItemDTO.setCategoria(foodItem.getCategoria());
        foodItemDTO.setQuantidade(foodItem.getQuantidade());
        foodItemDTO.setValidade(foodItem.getValidade());

        return foodItemDTO;
    }

    public FoodItem map(FoodItemDTO foodItemDTO) {
        FoodItem foodItem = new FoodItem();

        foodItem.setId(foodItemDTO.getId());
        foodItem.setNome(foodItemDTO.getNome());
        foodItem.setCategoria(foodItemDTO.getCategoria());
        foodItem.setQuantidade(foodItemDTO.getQuantidade());
        foodItem.setValidade(foodItemDTO.getValidade());

        return foodItem;
    }
}
