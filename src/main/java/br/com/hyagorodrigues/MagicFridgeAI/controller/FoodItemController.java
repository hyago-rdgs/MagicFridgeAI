package br.com.hyagorodrigues.MagicFridgeAI.controller;

import br.com.hyagorodrigues.MagicFridgeAI.dto.FoodItemDTO;
import br.com.hyagorodrigues.MagicFridgeAI.service.FoodItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/food")
public class FoodItemController {

    private final FoodItemService foodItemService;

    public FoodItemController(FoodItemService foodItemService) {
        this.foodItemService = foodItemService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<FoodItemDTO> cadastrar(@RequestBody FoodItemDTO foodItem) {
        FoodItemDTO novoRegistro = foodItemService.cadastrar(foodItem);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(novoRegistro);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<FoodItemDTO>> listar() {
        List<FoodItemDTO> listagem = foodItemService.listar();
        return ResponseEntity.ok().body(listagem);
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<?> listarPorId(@PathVariable Long id) {
        FoodItemDTO foodItem = foodItemService.listarPorId(id);

        if(foodItem != null) {
            return ResponseEntity.ok(foodItem);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Food Item #" + id + " nao foi encontrado!");
        }
    }

    @PutMapping("/atualizar")
    public ResponseEntity<String> atualizar(@PathVariable Long id, @RequestBody FoodItemDTO foodItem) {
        if(foodItemService.listarPorId(id) != null) {
            foodItemService.atualizar(id, foodItem);

            return ResponseEntity.ok("Food Item #" + id + " atualizado com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Food Item #" + id + " nao foi encontrado!");
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> excluir(@PathVariable Long id) {
        if(foodItemService.listarPorId(id) != null) {
            foodItemService.excluir(id);

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Food Item #" + id + " excluido com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Food Item #" + id + " nao foi encontrado!");
        }
    }
}
