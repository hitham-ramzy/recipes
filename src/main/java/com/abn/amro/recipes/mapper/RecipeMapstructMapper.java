package com.abn.amro.recipes.mapper;

import com.abn.amro.recipes.model.Recipe;
import com.abn.amro.recipes.model.dto.RecipeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RecipeMapstructMapper {

    RecipeMapstructMapper INSTANCE = Mappers.getMapper(RecipeMapstructMapper.class);

    Recipe mapDtoToRecipe(RecipeDTO recipeDTO);
}
