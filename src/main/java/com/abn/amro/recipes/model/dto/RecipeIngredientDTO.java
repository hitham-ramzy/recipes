package com.abn.amro.recipes.model.dto;

import com.abn.amro.recipes.model.enums.MeasurementUnit;
import lombok.Data;

@Data
public class RecipeIngredientDTO {
//     TODO :: ADD checks
    private Long ingredientId;

    private MeasurementUnit unit;

    private Double ingredientAmount;
}
