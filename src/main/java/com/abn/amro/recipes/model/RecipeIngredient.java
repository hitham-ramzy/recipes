package com.abn.amro.recipes.model;

import com.abn.amro.recipes.model.enums.MeasurementUnit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * The type Recipe ingredient.
 * This class has the ingredients for each Recipe and the amount of each ingredient as well.
 */
@Data
@Builder
@AllArgsConstructor
@Entity
@Table(name = "recipe_ingredient")
public class RecipeIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    private Recipe recipe;

    @ManyToOne
    private Ingredient ingredient;

    private MeasurementUnit measurementUnit;

    private Double amount;

    public RecipeIngredient() {

    }
}
