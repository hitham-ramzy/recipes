package com.abn.amro.recipes.mapper;

import com.abn.amro.recipes.model.Ingredient;
import com.abn.amro.recipes.model.dto.IngredientDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface IngredientMapstructMapper {

    IngredientMapstructMapper INSTANCE = Mappers.getMapper(IngredientMapstructMapper.class);


    Ingredient mapDtoToIngredient(IngredientDTO ingredientDTO);
}
