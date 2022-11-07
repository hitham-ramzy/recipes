package com.abn.amro.recipes.utils;

import com.abn.amro.recipes.model.Ingredient;
import com.abn.amro.recipes.model.RecipeType;
import com.abn.amro.recipes.model.dto.IngredientDTO;
import com.abn.amro.recipes.model.dto.RecipeDTO;
import com.abn.amro.recipes.model.dto.RecipeIngredientDTO;
import com.abn.amro.recipes.model.enums.MeasurementUnit;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.util.List;

public class TestUtils {

    public static Integer INGREDIENTS_SEED_DATA_SIZE = 5;

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static IngredientDTO buildRandomIngredientDTO(String name) {
        IngredientDTO ingredientDTO = new IngredientDTO();
        ingredientDTO.setName(name);
        return ingredientDTO;
    }

    public static Ingredient buildRandomIngredient() {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(RandomUtils.nextLong());
        ingredient.setName(RandomStringUtils.randomAlphabetic(10));
        return ingredient;
    }

    public static RecipeType buildRandomRecipeType() {
        RecipeType recipeType = new RecipeType();
        recipeType.setId(RandomUtils.nextLong());
        recipeType.setName(RandomStringUtils.randomAlphabetic(30));
        return recipeType;
    }

    public static RecipeDTO buildRandomRecipeDTO() {
        RecipeDTO recipeDTO = new RecipeDTO();
        recipeDTO.setName(RandomStringUtils.randomAlphabetic(10));
        recipeDTO.setRecipeTypeId(1L);
        recipeDTO.setInstructions(RandomStringUtils.randomAlphabetic(100));
        recipeDTO.setNumberOfServings(RandomUtils.nextInt());
        recipeDTO.setRecipeIngredientDTOS(List.of(buildRandomRecipeIngredientDTO(), buildRandomRecipeIngredientDTO(), buildRandomRecipeIngredientDTO()));
        return recipeDTO;
    }

    public static RecipeIngredientDTO buildRandomRecipeIngredientDTO() {
        RecipeIngredientDTO recipeIngredientDTO = new RecipeIngredientDTO();
        recipeIngredientDTO.setIngredientId(1L);
        recipeIngredientDTO.setUnit(buildRandomMeasurementUnit());
        recipeIngredientDTO.setIngredientAmount(RandomUtils.nextDouble());
        return recipeIngredientDTO;
    }

    public static MeasurementUnit buildRandomMeasurementUnit() {
        MeasurementUnit[] measurementUnits = MeasurementUnit.values();
        return measurementUnits[RandomUtils.nextInt(0, measurementUnits.length - 1)];
    }

}
