package com.abn.amro.recipes.model.criteria;

import com.abn.amro.recipes.model.RecipeType;
import lombok.Data;


@Data
public class RecipeCriteria {

    public static class RecipeTypeFilter extends Filter<RecipeType> {

    }

    public static class IntegerFilter extends Filter<Integer> {

    }

    private StringFilter name;

    private RecipeTypeFilter type;

    private StringFilter instructions;

    private IntegerFilter numberOfServings;
}
