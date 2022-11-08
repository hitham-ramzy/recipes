package com.abn.amro.recipes.model.dto;

import static com.abn.amro.recipes.utils.ErrorUtils.INSTRUCTIONS_LENGTH_MESSAGE;
import static com.abn.amro.recipes.utils.ErrorUtils.INSTRUCTIONS_NOT_NULL_MESSAGE;
import static com.abn.amro.recipes.utils.ErrorUtils.NAME_LENGTH_MESSAGE;
import static com.abn.amro.recipes.utils.ErrorUtils.NAME_NOT_NULL_MESSAGE;
import static com.abn.amro.recipes.utils.ErrorUtils.NUM_OF_SERVINGS_MAX_MESSAGE;
import static com.abn.amro.recipes.utils.ErrorUtils.NUM_OF_SERVINGS_MIN_MESSAGE;
import static com.abn.amro.recipes.utils.ErrorUtils.NUM_OF_SERVINGS_NOT_NULL;
import static com.abn.amro.recipes.utils.ErrorUtils.RECIPE_INGREDIENTS_NOT_NULL;
import static com.abn.amro.recipes.utils.ErrorUtils.RECIPE_INGREDIENTS_SIZE;
import static com.abn.amro.recipes.utils.ErrorUtils.RECIPE_TYPE_ID_NOT_NULL_MESSAGE;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class RecipeDTO {

    @NotNull(message = NAME_NOT_NULL_MESSAGE)
    @Size(min = 1, max = 100, message = NAME_LENGTH_MESSAGE)
    private String name;

    @NotNull(message = RECIPE_TYPE_ID_NOT_NULL_MESSAGE)
    private Long recipeTypeId;

    @Size(min = 10, max = 2500, message = INSTRUCTIONS_LENGTH_MESSAGE)
    @NotNull(message = INSTRUCTIONS_NOT_NULL_MESSAGE)
    private String instructions;


    @Min(value = 1, message = NUM_OF_SERVINGS_MIN_MESSAGE)
    @Max(value = 50, message = NUM_OF_SERVINGS_MAX_MESSAGE)
    @NotNull(message = NUM_OF_SERVINGS_NOT_NULL)
    private Integer numberOfServings;

    @Size(min = 1, message = RECIPE_INGREDIENTS_SIZE)
    @NotNull(message = RECIPE_INGREDIENTS_NOT_NULL)
    @Valid
    private Set<RecipeIngredientDTO> recipeIngredientDTOS;
}
