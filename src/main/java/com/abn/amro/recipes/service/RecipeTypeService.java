package com.abn.amro.recipes.service;

import com.abn.amro.recipes.model.Ingredient;
import com.abn.amro.recipes.model.RecipeType;
import com.abn.amro.recipes.repository.RecipeTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeTypeService {

    private final RecipeTypeRepository recipeTypeRepository;

    public RecipeTypeService(RecipeTypeRepository recipeTypeRepository) {
        this.recipeTypeRepository = recipeTypeRepository;
    }

    public RecipeType save(RecipeType recipeType) {
        validate(recipeType);
        return recipeTypeRepository.save(recipeType);
    }

    public RecipeType findById(Long id) {
        return recipeTypeRepository.findById(id).orElse(null);
    }

    public List<RecipeType> findAll() {
        return recipeTypeRepository.findAll();
    }

    private void validate(RecipeType recipeType) {
        RecipeType savedRecipeType = recipeTypeRepository.findOneByName(recipeType.getName());
        if (savedRecipeType != null) {
            throw new RuntimeException("Name Already Exist");
        }
    }
}
