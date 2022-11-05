package com.abn.amro.recipes.model.criteria;

import lombok.Data;


@Data
public class RecipeCriteria {

    private StringFilter name;

    private StringFilter recipeTypeName;

    private StringFilter instructions;

    private IntegerFilter numberOfServings;
}
