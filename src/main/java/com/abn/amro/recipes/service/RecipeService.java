package com.abn.amro.recipes.service;

import com.abn.amro.recipes.model.Recipe;
import com.abn.amro.recipes.repository.RecipeRepository;
import static com.abn.amro.recipes.utils.ErrorEnum.RECIPE_ALREADY_EXIST;
import static com.abn.amro.recipes.utils.ErrorUtils.generateInvalidInputError;
import static com.abn.amro.recipes.utils.ErrorUtils.generateNotFoundError;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public Recipe save(Recipe recipe) {
        if (recipeRepository.existsByName(recipe.getName())) {
            generateInvalidInputError(RECIPE_ALREADY_EXIST);
        }
        return recipeRepository.save(recipe);
    }

    public Recipe findById(Long id) {
        return recipeRepository.findById(id).orElse(null);
    }

    public List<Recipe> findAll(Specification<Recipe> specification) {
        return recipeRepository.findAll(specification);
    }

    public Boolean isRecipeTypeUsed(Long id) {
        return recipeRepository.existsByRecipeTypeId(id);
    }

    public void delete(Long id) {
        if (!recipeRepository.existsById(id)) {
            generateNotFoundError();
        }
        recipeRepository.deleteById(id);
    }
}
