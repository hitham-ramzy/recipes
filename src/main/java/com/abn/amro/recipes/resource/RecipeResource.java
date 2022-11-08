package com.abn.amro.recipes.resource;


import com.abn.amro.recipes.model.Recipe;
import com.abn.amro.recipes.model.criteria.RecipeCriteria;
import com.abn.amro.recipes.model.dto.RecipeDTO;
import com.abn.amro.recipes.service.RecipeQueryService;
import com.abn.amro.recipes.service.RecipeService;
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
@RequestMapping("/api/recipe")
public class RecipeResource {

    private final RecipeService recipeService;
    private final RecipeQueryService recipeQueryService;

    public RecipeResource(RecipeService recipeService, RecipeQueryService recipeQueryService) {
        this.recipeService = recipeService;
        this.recipeQueryService = recipeQueryService;
    }


    @ApiOperation(value = "Save Recipe", notes = "Returns the saved Recipe with generated id.")
    @PostMapping()
    public ResponseEntity<Recipe> save(@Valid @RequestBody RecipeDTO recipeDTO) {
        return ResponseEntity.ok(recipeQueryService.save(recipeDTO));
    }

    @ApiOperation(value = "Find Recipe by id", notes = "Returns the Recipe if exist.")
    @GetMapping("/{id}")
    public ResponseEntity<Recipe> findById(@PathVariable Long id) {
        return ResponseEntity.ok(recipeService.findById(id));
    }

    @ApiOperation(value = "Find All Recipe that match passed criteria",
            notes = "The combination works among these fields not among the criteria on the same field, So don't use the same field twice with different criteria (like using `name.contains` and `name.equals` at the same time).\n" +
            "\n" +
            "Example for a valid Search Criteria -> `name.contains=meatBall&recipeTypeName.notEquals=VEGETARIAN`\n" +
            "\n" +
            "Example for invalid Search Criteria -> `name.contains=meatBall&name.notEquals=chicken`")
    @GetMapping()
    public ResponseEntity<List<Recipe>> findAll(RecipeCriteria criteria) {
        return ResponseEntity.ok(recipeQueryService.findAll(criteria));
    }

    @ApiOperation(value = "Update Recipe by id", notes = "Returns the updated Recipe if exist.")
    @PutMapping("/{id}")
    public ResponseEntity<Recipe> update(@PathVariable Long id, @Valid @RequestBody RecipeDTO recipeDTO) {
        return ResponseEntity.ok(recipeQueryService.update(id, recipeDTO));
    }

    @ApiOperation(value = "Delete Recipe by id", notes = "Returns success or failed only")
    @DeleteMapping("/{id}")
    public ResponseEntity<Recipe> delete(@PathVariable Long id) {
        recipeService.delete(id);
        return ResponseEntity.ok().build();
    }

}
