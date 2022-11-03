package com.abn.amro.ingredients.service;

import com.abn.amro.recipes.entity.Ingredient;
import com.abn.amro.recipes.repository.IngredientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public Ingredient save(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    public Ingredient findById(Long id) {
        return ingredientRepository.findById(id).orElse(null);
    }

    public List<Ingredient> findAll() {
        return ingredientRepository.findAll();
    }

    public Page<Ingredient> findAll(Pageable pageable) {
        return ingredientRepository.findAll(pageable);
    }

    public Page<Ingredient> search(Specification<Ingredient> specification, Pageable pageable) {
        return ingredientRepository.findAll(specification, pageable);
    }
}
