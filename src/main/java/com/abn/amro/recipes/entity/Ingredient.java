package com.abn.amro.recipes.entity;

import com.abn.amro.recipes.entity.enums.IngredientUnit;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The type Ingredient and its unit used.
 */
@Data
@Entity
@Table(name = "ingredient")
public class Ingredient {

    @Id
    private Integer id;

    private IngredientUnit unit;
}
