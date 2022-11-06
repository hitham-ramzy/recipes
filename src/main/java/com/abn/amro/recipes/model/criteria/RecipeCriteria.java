package com.abn.amro.recipes.model.criteria;

import lombok.Data;


@Data
public class RecipeCriteria {

    private StringFilter name;

    private StringFilter instructions;

    private IntegerFilter numberOfServings;

    private LongFilter recipeTypeId;

    private StringFilter recipeTypeName;

    private LongFilter ingredientId;

    private StringFilter ingredientName;
}
