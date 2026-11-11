package br.com.hyagorodrigues.MagicFridgeAI.service;

import br.com.hyagorodrigues.MagicFridgeAI.dto.FoodItemDTO;
import br.com.hyagorodrigues.MagicFridgeAI.mapper.FoodItemMapper;
import br.com.hyagorodrigues.MagicFridgeAI.model.FoodItem;
import br.com.hyagorodrigues.MagicFridgeAI.repository.FoodItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FoodItemService {

    private final FoodItemRepository foodItemRepository;
    private final FoodItemMapper foodItemMapper;

    public FoodItemService(FoodItemRepository foodItemRepository, FoodItemMapper foodItemMapper) {
        this.foodItemRepository = foodItemRepository;
        this.foodItemMapper = foodItemMapper;
    }

    public FoodItemDTO cadastrar(FoodItemDTO foodItemDTO) {
        FoodItem foodItem = foodItemMapper.map(foodItemDTO);
        foodItem = foodItemRepository.save(foodItem);

        return foodItemMapper.map(foodItem);
    }

    public List<FoodItemDTO> listar() {
        List<FoodItem> foodItems = foodItemRepository.findAll();

        return foodItems.stream()
                .map(foodItemMapper::map)
                .collect(Collectors.toList());
    }

    public FoodItemDTO listarPorId(Long id) {
        Optional<FoodItem> foodItem = foodItemRepository.findById(id);

        return foodItem
                .map(foodItemMapper::map)
                .orElse(null);
    }

    public FoodItemDTO atualizar(Long id, FoodItemDTO foodItem) {
        Optional<FoodItem> foodItemExistente = foodItemRepository.findById(id);

        if(foodItemExistente.isPresent()) {
            FoodItem foodItemAtualizado = foodItemMapper.map(foodItem);
            foodItemAtualizado.setId(id);

            foodItemAtualizado = foodItemRepository.save(foodItemAtualizado);
            return foodItemMapper.map(foodItemAtualizado);
        }

        return null;
    }

    public void excluir(Long id) {
        foodItemRepository.deleteById(id);
    }
}
