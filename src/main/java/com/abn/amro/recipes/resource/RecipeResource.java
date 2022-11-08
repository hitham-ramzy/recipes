package com.abn.amro.recipes.resource;


import com.abn.amro.recipes.model.Recipe;
import com.abn.amro.recipes.model.criteria.RecipeCriteria;
import com.abn.amro.recipes.model.dto.RecipeDTO;
import com.abn.amro.recipes.service.RecipeQueryService;
import com.abn.amro.recipes.service.RecipeService;
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
@RequestMapping("/api/recipe")
public class RecipeResource {

    private final RecipeService recipeService;
    private final RecipeQueryService recipeQueryService;

    public RecipeResource(RecipeService recipeService, RecipeQueryService recipeQueryService) {
        this.recipeService = recipeService;
        this.recipeQueryService = recipeQueryService;
    }

    @PostMapping()
    public ResponseEntity<Recipe> save(@Valid @RequestBody RecipeDTO recipeDTO) {
        return ResponseEntity.ok(recipeQueryService.save(recipeDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recipe> findById(@PathVariable Long id) {
        return ResponseEntity.ok(recipeService.findById(id));
    }

    @GetMapping()
    public ResponseEntity<List<Recipe>> findAll(RecipeCriteria criteria) {
        return ResponseEntity.ok(recipeQueryService.findAll(criteria));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Recipe> update(@PathVariable Long id, @Valid @RequestBody RecipeDTO recipeDTO) {
        return ResponseEntity.ok(recipeQueryService.update(id, recipeDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Recipe> delete(@PathVariable Long id) {
        recipeService.delete(id);
        return ResponseEntity.ok().build();
    }

}
