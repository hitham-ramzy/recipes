package com.abn.amro.recipes.repository;

import com.abn.amro.recipes.model.RecipeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeTypeRepository extends JpaRepository<RecipeType, Long>, JpaSpecificationExecutor<RecipeType> {
    RecipeType findOneByName(String name);
}
