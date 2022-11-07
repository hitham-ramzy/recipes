package com.abn.amro.recipes.service;

import com.abn.amro.recipes.model.Ingredient;
import static com.abn.amro.recipes.utils.TestUtils.buildRandomIngredientDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class IngredientQueryServiceTest {

    @Mock
    private IngredientService ingredientService;

    @InjectMocks
    IngredientQueryService ingredientQueryService;

    @Test
    void save_IngredientDTO() {
        ingredientQueryService.save(buildRandomIngredientDTO());
        verify(ingredientService, only()).save(any(Ingredient.class));
    }

}