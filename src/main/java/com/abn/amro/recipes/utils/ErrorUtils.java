package com.abn.amro.recipes.utils;

public class ErrorUtils {

    public static final String NAME_LENGTH_MESSAGE = "Name length should be between 1 and 100 characters";
    public static final String NAME_NOT_NULL_MESSAGE = "Name shouldn't be null";
    public static final String INSTRUCTIONS_LENGTH_MESSAGE = "Instructions length should be between 10 and 2500 characters";
    public static final String INSTRUCTIONS_NOT_NULL_MESSAGE = "Instructions shouldn't be null";
    public static final String NUM_OF_SERVINGS_MIN_MESSAGE = "Number of services length should be greater than 1";
    public static final String NUM_OF_SERVINGS_MAX_MESSAGE = "Number of services length should be less than 50";
    public static final String NUM_OF_SERVINGS_NOT_NULL = "Instructions shouldn't be null";
    public static final String INGREDIENT_ID_NOT_NULL_MESSAGE = "Ingredient Id shouldn't be null";
    public static final String MEASUREMENT_UNIT_NOT_NULL_MESSAGE = "Ingredient Id shouldn't be null";
    public static final String INGREDIENT_AMOUNT_NOT_NULL_MESSAGE = "Ingredient amount shouldn't be null";
    public static final String RECIPE_INGREDIENTS_SIZE = "Recipe should have at least one Ingredient";

    public static void generateError(ErrorConstant constant) {
        throw new RuntimeException(constant.getMessage());
    }

}
