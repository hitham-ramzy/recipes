package com.abn.amro.recipes.service;

import com.abn.amro.recipes.model.Ingredient;
import com.abn.amro.recipes.model.dto.IngredientDTO;
import com.abn.amro.recipes.repository.IngredientRepository;
import static com.abn.amro.recipes.utils.ErrorConstant.INGREDIENT_ALREADY_USED;
import static com.abn.amro.recipes.utils.ErrorConstant.INGREDIENT_NAME_NOT_CHANGED;
import static com.abn.amro.recipes.utils.ErrorConstant.INGREDIENT_NOT_EXIST;
import static com.abn.amro.recipes.utils.ErrorConstant.NAME_ALREADY_EXIST;
import static com.abn.amro.recipes.utils.ErrorUtils.generateError;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {

    private final IngredientRepository ingredientRepository;
    private final RecipeIngredientService ingredientService;


    public IngredientService(IngredientRepository ingredientRepository, RecipeIngredientService ingredientService) {
        this.ingredientRepository = ingredientRepository;
        this.ingredientService = ingredientService;
    }

    public Ingredient save(Ingredient ingredient) {
        validateName(ingredient.getName());
        return ingredientRepository.save(ingredient);
    }

    public Ingredient findById(Long id) {
        return ingredientRepository.findById(id).orElse(null);
    }

    public List<Ingredient> findByIds(List<Long> ids) {
        return ingredientRepository.findAllById(ids);
    }

    public List<Ingredient> findAll() {
        return ingredientRepository.findAll();
    }

    public Ingredient update(Long id, IngredientDTO ingredientDTO) {
        Ingredient savedIngredient = ingredientRepository.findById(id).orElse(null);
        if (savedIngredient == null) {
            generateError(INGREDIENT_NOT_EXIST);
        } else if (savedIngredient.getName().equals(ingredientDTO.getName())) {
            generateError(INGREDIENT_NAME_NOT_CHANGED);
        }
        validateName(ingredientDTO.getName());
        savedIngredient.setName(ingredientDTO.getName());
        return ingredientRepository.save(savedIngredient);
    }

    public void delete(Long id) {
        Boolean isIngredientExist = ingredientRepository.existsById(id);
        if (!isIngredientExist) {
            generateError(INGREDIENT_NOT_EXIST);
        }

        Boolean isIngredientUsed = ingredientService.isIngredientUsed(id);
        if (isIngredientUsed) {
            generateError(INGREDIENT_ALREADY_USED);
        }
        ingredientRepository.deleteById(id);
    }


    private void validateName(String ingredientName) {
        Ingredient savedIngredient = ingredientRepository.findOneByName(ingredientName);
        if (savedIngredient != null) {
            generateError(NAME_ALREADY_EXIST);
        }
    }
}
