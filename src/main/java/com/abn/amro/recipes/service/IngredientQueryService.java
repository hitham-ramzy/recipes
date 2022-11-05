package com.abn.amro.recipes.service;

import com.abn.amro.recipes.mapper.IngredientMapper;
import com.abn.amro.recipes.model.Ingredient;
import com.abn.amro.recipes.model.dto.IngredientDTO;
import org.springframework.stereotype.Service;

@Service
public class IngredientQueryService {

    private final IngredientService ingredientService;

    public IngredientQueryService(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    public Ingredient save(IngredientDTO ingredientDTO) {
        Ingredient ingredient = IngredientMapper.INSTANCE.mapDtoToIngredient(ingredientDTO);
        return ingredientService.save(ingredient);
    }
}
