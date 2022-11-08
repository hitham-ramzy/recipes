package com.abn.amro.recipes.resource;


import com.abn.amro.recipes.mapper.RecipeTypeMapper;
import com.abn.amro.recipes.model.RecipeType;
import com.abn.amro.recipes.model.dto.RecipeTypeDTO;
import com.abn.amro.recipes.service.RecipeTypeService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@RequestMapping("/api/recipe-type")
public class RecipeTypeResource {

    private final RecipeTypeService recipeTypeService;

    public RecipeTypeResource(RecipeTypeService recipeTypeService) {
        this.recipeTypeService = recipeTypeService;
    }

    @ApiOperation(value = "Save Recipe Type", notes = "Returns the saved Recipe Type with generated id.")
    @PostMapping()
    public ResponseEntity<RecipeType> save(@Valid @RequestBody RecipeTypeDTO recipeTypeDTO) {
        RecipeType recipeType = RecipeTypeMapper.INSTANCE.mapDtoToRecipe(recipeTypeDTO);
        return ResponseEntity.ok(recipeTypeService.save(recipeType));
    }


    @ApiOperation(value = "Find Recipe Type by id", notes = "Returns the Recipe Type if exist.")
    @GetMapping("/{id}")
    public ResponseEntity<RecipeType> findById(@PathVariable Long id) {
        return ResponseEntity.ok(recipeTypeService.findById(id));
    }

    @ApiOperation(value = "Find All Recipe Types", notes = "Returns All Recipe Type if exist.")
    @GetMapping()
    public ResponseEntity<List<RecipeType>> findAll() {
        return ResponseEntity.ok(recipeTypeService.findAll());
    }


    @ApiOperation(value = "Update Recipe Type by id", notes = "Returns the updated Recipe Type if exist.")
    @PutMapping("/{id}")
    public ResponseEntity<RecipeType> update(@PathVariable Long id, @Valid @RequestBody RecipeTypeDTO recipeTypeDTO) {
        return ResponseEntity.ok(recipeTypeService.update(id, recipeTypeDTO));
    }

    @ApiOperation(value = "Delete Recipe Type by id", notes = "Returns success or failed only")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        recipeTypeService.delete(id);
        return ResponseEntity.ok().build();
    }
}
