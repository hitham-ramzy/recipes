package com.abn.amro.recipes.model.dto;

import static com.abn.amro.recipes.utils.ErrorConstants.NAME_LENGTH_MESSAGE;
import static com.abn.amro.recipes.utils.ErrorConstants.NAME_NOT_NULL_MESSAGE;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RecipeTypeDTO {

    @NotNull(message = NAME_NOT_NULL_MESSAGE)
    @Size(min = 1, max = 100, message = NAME_LENGTH_MESSAGE)
    private String name;
}
