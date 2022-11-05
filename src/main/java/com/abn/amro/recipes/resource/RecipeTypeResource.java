package com.abn.amro.recipes.resource;


import com.abn.amro.recipes.mapper.RecipeTypeMapper;
import com.abn.amro.recipes.model.RecipeType;
import com.abn.amro.recipes.model.dto.RecipeTypeDTO;
import com.abn.amro.recipes.service.RecipeTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/recipe-type")
public class RecipeTypeResource {

    private final RecipeTypeService recipeTypeService;

    public RecipeTypeResource(RecipeTypeService recipeTypeService) {
        this.recipeTypeService = recipeTypeService;
    }

    @PostMapping()
    public ResponseEntity<RecipeType> save(@RequestBody RecipeTypeDTO recipeTypeDTO) {
        RecipeType recipeType = RecipeTypeMapper.INSTANCE.mapDtoToRecipe(recipeTypeDTO);
        return ResponseEntity.ok(recipeTypeService.save(recipeType));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeType> findById(@PathVariable Long id) {
        return ResponseEntity.ok(recipeTypeService.findById(id));
    }

    @GetMapping()
    public ResponseEntity<List<RecipeType>> findAll() {
        return ResponseEntity.ok(recipeTypeService.findAll());
    }

}
