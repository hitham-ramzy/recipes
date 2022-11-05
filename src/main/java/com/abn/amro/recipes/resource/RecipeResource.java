package com.abn.amro.recipes.resource;


import com.abn.amro.recipes.model.Recipe;
import com.abn.amro.recipes.model.criteria.RecipeCriteria;
import com.abn.amro.recipes.model.dto.RecipeDTO;
import com.abn.amro.recipes.service.RecipeQueryService;
import com.abn.amro.recipes.service.RecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<Recipe> save(@RequestBody RecipeDTO recipeDTO) {
        return ResponseEntity.ok(recipeQueryService.save(recipeDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recipe> findById(@PathVariable Long id) {
        return ResponseEntity.ok(recipeService.findById(id));
    }

    @GetMapping()
    public ResponseEntity<List<Recipe>> findBy(RecipeCriteria criteria) {
        return ResponseEntity.ok(recipeQueryService.findAll(criteria));
    }

}
