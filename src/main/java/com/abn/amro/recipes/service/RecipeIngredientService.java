package com.abn.amro.recipes.service;

import com.abn.amro.recipes.model.RecipeIngredient;
import com.abn.amro.recipes.repository.RecipeIngredientRepository;
import org.springframework.stereotype.Service;

@Service
public class RecipeIngredientService {

    private final RecipeIngredientRepository recipeIngredientRepository;

    public RecipeIngredientService(RecipeIngredientRepository recipeIngredientRepository) {
        this.recipeIngredientRepository = recipeIngredientRepository;
    }

    public Boolean isIngredientUsed(Long ingredientId){
        return recipeIngredientRepository.existsByIngredientId(ingredientId);
    }

}
