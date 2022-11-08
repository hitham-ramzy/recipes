package com.abn.amro.recipes.resource;


import com.abn.amro.recipes.model.Ingredient;
import com.abn.amro.recipes.model.dto.IngredientDTO;
import com.abn.amro.recipes.service.IngredientQueryService;
import com.abn.amro.recipes.service.IngredientService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
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

    @ApiOperation(value = "Save Ingredient", notes = "Returns the Ingredient with the generated id")
    @PostMapping()
    public ResponseEntity<Ingredient> save(@Valid @RequestBody IngredientDTO ingredientDTO) {
        return ResponseEntity.ok(ingredientQueryService.save(ingredientDTO));
    }

    @ApiOperation(value = "Get all Ingredients", notes = "Returns all Ingredient")
    @GetMapping()
    public ResponseEntity<List<Ingredient>> findAll() {
        return ResponseEntity.ok(ingredientService.findAll());
    }

    @ApiOperation(value = "Get one Ingredient by passing its id", notes = "Returns one Ingredient, if exists")
    @GetMapping("/{id}")
    public ResponseEntity<Ingredient> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ingredientService.findById(id));
    }

    @ApiOperation(value = "Update an ingredient", notes = "Returns the updated Ingredient, if exists")
    @PutMapping("/{id}")
    public ResponseEntity<Ingredient> update(@PathVariable Long id, @Valid @RequestBody IngredientDTO ingredientDTO) {
        return ResponseEntity.ok(ingredientService.update(id, ingredientDTO));
    }

    @ApiOperation(value = "Delete one Ingredient by passing its id", notes = "Returns success or failed only")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ingredientService.delete(id);
        return ResponseEntity.ok().build();
    }

}
