package com.abn.amro.recipes.service;

import com.abn.amro.recipes.model.RecipeType;
import com.abn.amro.recipes.model.dto.RecipeTypeDTO;
import com.abn.amro.recipes.repository.RecipeTypeRepository;
import static com.abn.amro.recipes.utils.ErrorEnum.NAME_ALREADY_EXIST;
import static com.abn.amro.recipes.utils.ErrorEnum.RECIPE_TYPE_ALREADY_USED;
import static com.abn.amro.recipes.utils.ErrorEnum.RECIPE_TYPE_NAME_NOT_CHANGED;
import static com.abn.amro.recipes.utils.ErrorEnum.RECIPE_TYPE_NOT_EXIST;
import static com.abn.amro.recipes.utils.ErrorUtils.generateInvalidInputError;
import static com.abn.amro.recipes.utils.ErrorUtils.generateNotFoundError;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeTypeService {

    private final RecipeTypeRepository recipeTypeRepository;

    private final RecipeService recipeService;

    public RecipeTypeService(RecipeTypeRepository recipeTypeRepository, RecipeService recipeService) {
        this.recipeTypeRepository = recipeTypeRepository;
        this.recipeService = recipeService;
    }

    public RecipeType save(RecipeType recipeType) {
        validateName(recipeType.getName());
        return recipeTypeRepository.save(recipeType);
    }

    public RecipeType findById(Long id) {
        return recipeTypeRepository.findById(id).orElse(null);
    }

    public List<RecipeType> findAll() {
        return recipeTypeRepository.findAll();
    }

    public RecipeType update(Long id, RecipeTypeDTO recipeTypeDTO) {
        RecipeType savedRecipeType = recipeTypeRepository.findById(id).orElse(null);
        if (savedRecipeType == null) {
            generateNotFoundError();
        } else if (savedRecipeType.getName().equals(recipeTypeDTO.getName())) {
            generateInvalidInputError(RECIPE_TYPE_NAME_NOT_CHANGED);
        }
        validateName(recipeTypeDTO.getName());
        savedRecipeType.setName(recipeTypeDTO.getName());
        return recipeTypeRepository.save(savedRecipeType);
    }

    public void delete(Long id) {
        if (!recipeTypeRepository.existsById(id)) {
            generateNotFoundError();
        }

        Boolean isRecipeTypeUsed = recipeService.isRecipeTypeUsed(id);
        if (isRecipeTypeUsed) {
            generateInvalidInputError(RECIPE_TYPE_ALREADY_USED);
        }
        recipeTypeRepository.deleteById(id);
    }

    private void validateName(String recipeTypeName) {
        RecipeType savedRecipeType = recipeTypeRepository.findOneByNameIgnoreCase(recipeTypeName);
        if (savedRecipeType != null) {
            generateInvalidInputError(NAME_ALREADY_EXIST);
        }
    }
}
