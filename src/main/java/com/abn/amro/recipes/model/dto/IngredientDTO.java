package com.abn.amro.recipes.model.dto;

import com.abn.amro.recipes.model.enums.IngredientUnit;
import lombok.Data;

@Data
public class IngredientDTO {
    // TODO :: ADD CHECKS

    private String name;

    private IngredientUnit unit;
}
