package com.abn.amro.recipes.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * The type Recipe ingredient.
 * This class has the ingredients for each Recipe and the amount of each ingredient as well.
 */
@Data
@Entity
@Table(name = "recipe_ingredient")
public class RecipeIngredient {

    @Id
    private Integer id;

    @ManyToOne
    private Ingredient ingredient;

    private Double amount;
}
