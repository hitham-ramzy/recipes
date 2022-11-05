package com.abn.amro.recipes.model.dto;

import com.abn.amro.recipes.model.enums.RecipeType;
import lombok.Data;

import java.util.List;

@Data
public class RecipeDTO {
//    TODO :: add checks

    private String name;

    private RecipeType type;

    private String instructions;

    private Integer numberOfServings;

    private List<RecipeIngredientDTO> recipeIngredientDTOS;
}
