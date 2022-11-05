package com.abn.amro.recipes.service;

import com.abn.amro.recipes.mapper.IngredientMapper;
import com.abn.amro.recipes.model.Ingredient;
import com.abn.amro.recipes.model.dto.IngredientDTO;
import com.abn.amro.recipes.repository.IngredientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public Ingredient save(IngredientDTO ingredientDTO) {
        // TODO :: think about move the mapping into different layer, as the service layer should not depend on the DTOs
        Ingredient ingredient = IngredientMapper.INSTANCE.mapDtoToIngredient(ingredientDTO);
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
}
