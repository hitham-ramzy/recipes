package com.abn.amro.recipes.mapper;

import com.abn.amro.recipes.model.RecipeType;
import com.abn.amro.recipes.model.dto.RecipeTypeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RecipeTypeMapper {

    RecipeTypeMapper INSTANCE = Mappers.getMapper(RecipeTypeMapper.class);

    RecipeType mapDtoToRecipe(RecipeTypeDTO recipeTypeDTO);
}
