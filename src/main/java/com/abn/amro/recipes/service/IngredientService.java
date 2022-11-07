package com.abn.amro.recipes.service;

import com.abn.amro.recipes.model.Ingredient;
import com.abn.amro.recipes.repository.IngredientRepository;
import static com.abn.amro.recipes.utils.ErrorConstant.NAME_ALREADY_EXIST;
import static com.abn.amro.recipes.utils.ErrorUtils.generateError;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public Ingredient save(Ingredient ingredient) {
        validate(ingredient);
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


    private void validate(Ingredient ingredient) {
        Ingredient savedIngredient = ingredientRepository.findOneByName(ingredient.getName());
        if (savedIngredient != null) {
            generateError(NAME_ALREADY_EXIST);
        }
    }
}
