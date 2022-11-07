package com.abn.amro.recipes.model.dto;

import static com.abn.amro.recipes.utils.ErrorUtils.NAME_FIELD_LENGTH;
import static com.abn.amro.recipes.utils.ErrorUtils.NAME_FIELD_NOT_NULL;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class IngredientDTO {

    @NotNull(message = NAME_FIELD_NOT_NULL)
    @Size(min = 1, max = 100, message = NAME_FIELD_LENGTH)
    private String name;
}
