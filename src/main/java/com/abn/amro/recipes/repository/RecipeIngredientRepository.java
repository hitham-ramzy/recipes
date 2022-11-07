package com.abn.amro.recipes.repository;

import com.abn.amro.recipes.model.RecipeIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, Long>, JpaSpecificationExecutor<RecipeIngredient> {
    Boolean existsByIngredientId(Long ingredientId);
}
