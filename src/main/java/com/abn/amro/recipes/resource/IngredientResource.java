package com.abn.amro.recipes.resource;


import com.abn.amro.recipes.model.Ingredient;
import com.abn.amro.recipes.model.dto.IngredientDTO;
import com.abn.amro.recipes.service.IngredientQueryService;
import com.abn.amro.recipes.service.IngredientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/ingredient")
public class IngredientResource {

    private final IngredientService ingredientService;
    private final IngredientQueryService ingredientQueryService;

    public IngredientResource(IngredientService ingredientService, IngredientQueryService ingredientQueryService) {
        this.ingredientService = ingredientService;
        this.ingredientQueryService = ingredientQueryService;
    }

    @PostMapping()
    public ResponseEntity<Ingredient> save(@RequestBody IngredientDTO ingredientDTO) {
        return ResponseEntity.ok(ingredientQueryService.save(ingredientDTO));
    }

    @GetMapping()
    public ResponseEntity<List<Ingredient>> findAll() {
        return ResponseEntity.ok(ingredientService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ingredient> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ingredientService.findById(id));
    }

}
