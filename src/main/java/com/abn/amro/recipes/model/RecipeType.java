package com.abn.amro.recipes.model;

import static com.abn.amro.recipes.utils.ErrorUtils.NAME_LENGTH_MESSAGE;
import static com.abn.amro.recipes.utils.ErrorUtils.NAME_NOT_NULL_MESSAGE;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * The type Recipe type.
 */

@Data
@Entity
@Table(name = "recipe_type")
public class RecipeType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotBlank(message = NAME_NOT_NULL_MESSAGE)
    @Size(min = 1, max = 100, message = NAME_LENGTH_MESSAGE)
    private String name;

}
