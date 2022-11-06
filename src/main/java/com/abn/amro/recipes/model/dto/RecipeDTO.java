package com.abn.amro.recipes.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class RecipeDTO {
//    TODO :: add checks

    private String name;

    private Long recipeTypeId;

    private String instructions;

    private Integer numberOfServings;

    private List<RecipeIngredientDTO> recipeIngredientDTOS;
}
