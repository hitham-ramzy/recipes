package com.abn.amro.recipes.utils;

import com.abn.amro.recipes.model.Ingredient;
import com.abn.amro.recipes.model.RecipeType;
import com.abn.amro.recipes.model.dto.IngredientDTO;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

public class TestUtils {

    public static IngredientDTO buildRandomIngredientDTO() {
        IngredientDTO ingredientDTO = new IngredientDTO();
        ingredientDTO.setName(RandomStringUtils.random(10));
        return ingredientDTO;
    }

    public static Ingredient buildRandomIngredient() {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(RandomUtils.nextLong());
        ingredient.setName(RandomStringUtils.random(10));
        return ingredient;
    }

    public static RecipeType buildRandomRecipeType() {
        RecipeType recipeType = new RecipeType();
        recipeType.setId(RandomUtils.nextLong());
        recipeType.setName(RandomStringUtils.random(30));
        return recipeType;
    }
}
