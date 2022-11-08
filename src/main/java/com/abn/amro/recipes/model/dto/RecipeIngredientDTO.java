package com.abn.amro.recipes.model.dto;

import com.abn.amro.recipes.model.enums.MeasurementUnit;
import static com.abn.amro.recipes.utils.ErrorConstants.INGREDIENT_AMOUNT_NOT_NULL_MESSAGE;
import static com.abn.amro.recipes.utils.ErrorConstants.INGREDIENT_ID_NOT_NULL_MESSAGE;
import static com.abn.amro.recipes.utils.ErrorConstants.MEASUREMENT_UNIT_NOT_NULL_MESSAGE;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@Data
public class RecipeIngredientDTO {

    @NotNull(message = INGREDIENT_ID_NOT_NULL_MESSAGE)
    private Long ingredientId;

    @NotNull(message = MEASUREMENT_UNIT_NOT_NULL_MESSAGE)
    private MeasurementUnit unit;

    @NotNull(message = INGREDIENT_AMOUNT_NOT_NULL_MESSAGE)
    private Double ingredientAmount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipeIngredientDTO that = (RecipeIngredientDTO) o;
        return ingredientId.equals(that.ingredientId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ingredientId);
    }
}
