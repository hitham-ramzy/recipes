package com.abn.amro.recipes.utils;

import com.abn.amro.recipes.model.Ingredient;
import com.abn.amro.recipes.model.RecipeType;
import com.abn.amro.recipes.model.dto.IngredientDTO;
import com.abn.amro.recipes.model.dto.RecipeDTO;
import com.abn.amro.recipes.model.dto.RecipeIngredientDTO;
import com.abn.amro.recipes.model.dto.RecipeTypeDTO;
import com.abn.amro.recipes.model.enums.MeasurementUnit;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestUtils {

    public static Integer INGREDIENTS_SEED_DATA_SIZE = 5;
    public static Integer RECIPE_TYPE_SEED_DATA_SIZE = 2;
    public static Integer RECIPE_SEED_DATA_SIZE = 1;

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
        recipeDTO.setRecipeTypeId(RandomUtils.nextLong(1, 2));
        recipeDTO.setInstructions(RandomStringUtils.randomAlphabetic(100));
        recipeDTO.setNumberOfServings(RandomUtils.nextInt(1, 50));
        recipeDTO.setRecipeIngredientDTOS(buildRandomListOfRecipeIngredientDTO());
        return recipeDTO;
    }

    public static RecipeDTO buildRecipeDTO(String name, Long recipeTypeId, String instructions,
                                                 Integer numberOfServings, Set<RecipeIngredientDTO> recipeIngredientDTOS) {
        RecipeDTO recipeDTO = new RecipeDTO();
        recipeDTO.setName(name);
        recipeDTO.setRecipeTypeId(recipeTypeId);
        recipeDTO.setInstructions(instructions);
        recipeDTO.setNumberOfServings(numberOfServings);
        recipeDTO.setRecipeIngredientDTOS(recipeIngredientDTOS);
        return recipeDTO;
    }

    public static Set<RecipeIngredientDTO> buildRandomListOfRecipeIngredientDTO() {
        return IntStream.rangeClosed(1, RandomUtils.nextInt(2, 5))
                .mapToObj(s -> buildRandomRecipeIngredientDTO())
                .collect(Collectors.toSet());
    }

    public static RecipeIngredientDTO buildRandomRecipeIngredientDTO() {
        RecipeIngredientDTO recipeIngredientDTO = new RecipeIngredientDTO();
        recipeIngredientDTO.setIngredientId(RandomUtils.nextLong(1, 5));
        recipeIngredientDTO.setUnit(buildRandomMeasurementUnit());
        recipeIngredientDTO.setIngredientAmount(RandomUtils.nextDouble());
        return recipeIngredientDTO;
    }

    public static RecipeIngredientDTO buildRandomRecipeIngredientDTO(Long ingredientId, MeasurementUnit unit, Double ingredientAmount) {
        RecipeIngredientDTO recipeIngredientDTO = new RecipeIngredientDTO();
        recipeIngredientDTO.setIngredientId(ingredientId);
        recipeIngredientDTO.setUnit(unit);
        recipeIngredientDTO.setIngredientAmount(ingredientAmount);
        return recipeIngredientDTO;
    }

    public static RecipeTypeDTO buildRandomRecipeTypeDTO(String name) {
        RecipeTypeDTO recipeTypeDTO = new RecipeTypeDTO();
        recipeTypeDTO.setName(name);
        return recipeTypeDTO;
    }

    public static MeasurementUnit buildRandomMeasurementUnit() {
        MeasurementUnit[] measurementUnits = MeasurementUnit.values();
        return measurementUnits[RandomUtils.nextInt(0, measurementUnits.length - 1)];
    }

}
