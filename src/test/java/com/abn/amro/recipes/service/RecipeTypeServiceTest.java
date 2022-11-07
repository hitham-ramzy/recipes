package com.abn.amro.recipes.service;

import com.abn.amro.recipes.model.RecipeType;
import com.abn.amro.recipes.repository.RecipeTypeRepository;
import static com.abn.amro.recipes.utils.ErrorConstant.NAME_ALREADY_EXIST;
import static com.abn.amro.recipes.utils.TestUtils.buildRandomRecipeType;
import org.apache.commons.lang3.RandomUtils;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RecipeTypeServiceTest {

    @Mock
    private RecipeTypeRepository recipeTypeRepository;

    @InjectMocks
    private RecipeTypeService recipeTypeService;

    @Test
    void save_validRecipeType_shouldCallRepositoryTwice() {
        RecipeType recipeType = buildRandomRecipeType();
        when(recipeTypeRepository.findOneByName(eq(recipeType.getName()))).thenReturn(null);
        recipeTypeService.save(recipeType);
        verify(recipeTypeRepository, times(1)).findOneByName(eq(recipeType.getName()));
        verify(recipeTypeRepository, times(1)).save(recipeType);
    }

    @Test
    void save_invalidRecipeType_shouldThrowException() {
        RecipeType recipeType = buildRandomRecipeType();
        when(recipeTypeRepository.findOneByName(eq(recipeType.getName()))).thenReturn(buildRandomRecipeType());
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> recipeTypeService.save(recipeType))
                .withMessage(NAME_ALREADY_EXIST.name());
    }

    @Test
    void findById_shouldCallRepositoryOnce() {
        Long id = RandomUtils.nextLong();
        recipeTypeService.findById(id);
        verify(recipeTypeRepository, only()).findById(eq(id));
    }

    @Test
    void findAll_shouldCallRepositoryOnce() {
        recipeTypeService.findAll();
        verify(recipeTypeRepository, only()).findAll();
    }
}