package com.abn.amro.recipes.entity;

import com.abn.amro.recipes.entity.enums.RecipeType;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * The type Recipe.
 */
@Data
@Entity
@Table(name = "recipe")
public class Recipe {

    @Id
    private Integer id;

    private String name;

    private RecipeType type;

    private String instructions;

    private Integer numberOfServings;

    @OneToMany
    private List<RecipeIngredient> ingredients;
}
