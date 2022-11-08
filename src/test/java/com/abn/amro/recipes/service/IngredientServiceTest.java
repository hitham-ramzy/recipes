package com.abn.amro.recipes.service;

import com.abn.amro.recipes.model.Ingredient;
import com.abn.amro.recipes.repository.IngredientRepository;
import static com.abn.amro.recipes.utils.ErrorEnum.NAME_ALREADY_EXIST;
import static com.abn.amro.recipes.utils.TestUtils.buildRandomIngredient;
import org.apache.commons.lang3.RandomUtils;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class IngredientServiceTest {

    @Mock
    private IngredientRepository ingredientRepository;

    @InjectMocks
    private IngredientService ingredientService;

    @Test
    public void save_validIngredient_shouldCallRepositoryTwice() {
        Ingredient ingredient = buildRandomIngredient();
        when(ingredientRepository.findOneByNameIgnoreCase(eq(ingredient.getName()))).thenReturn(null);
        when(ingredientRepository.save(any(Ingredient.class))).thenReturn(ingredient);
        Ingredient savedIngredient = ingredientService.save(ingredient);
        assertThat(savedIngredient.getName()).isEqualTo(ingredient.getName());
        verify(ingredientRepository, times(1)).findOneByNameIgnoreCase(anyString());
        verify(ingredientRepository, times(1)).save(any(Ingredient.class));
    }

    @Test
    public void save_repeatedIngredient_shouldThrowException() {
        Ingredient ingredient = buildRandomIngredient();
        when(ingredientRepository.findOneByNameIgnoreCase(eq(ingredient.getName()))).thenReturn(buildRandomIngredient());
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> ingredientService.save(ingredient))
                .withMessage(NAME_ALREADY_EXIST.getMessage());
    }

    @Test
    public void findById_shouldCallRepositoryOnce() {
        long id = RandomUtils.nextLong();
        ingredientService.findById(id);
        verify(ingredientRepository, only()).findById(eq(id));
    }

    @Test
    public void findByIds_shouldCallRepositoryOnce() {
        List<Long> ids = List.of(RandomUtils.nextLong(), RandomUtils.nextLong(), RandomUtils.nextLong());
        ingredientService.findByIds(ids);
        verify(ingredientRepository, only()).findAllById(any());
    }

    @Test
    public void findAll_shouldCallRepositoryOnce() {
        ingredientService.findAll();
        verify(ingredientRepository, only()).findAll();
    }


}