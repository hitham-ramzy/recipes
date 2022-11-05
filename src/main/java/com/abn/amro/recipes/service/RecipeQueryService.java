package com.abn.amro.recipes.service;

import com.abn.amro.recipes.mapper.RecipeMapstructMapper;
import com.abn.amro.recipes.model.Ingredient;
import com.abn.amro.recipes.model.Recipe;
import com.abn.amro.recipes.model.RecipeIngredient;
import com.abn.amro.recipes.model.criteria.RecipeCriteria;
import com.abn.amro.recipes.model.dto.RecipeDTO;
import com.abn.amro.recipes.model.dto.RecipeIngredientDTO;
import static com.abn.amro.recipes.utils.SpecificationUtils.buildSpecification;
import static com.abn.amro.recipes.utils.SpecificationUtils.buildStringSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class RecipeQueryService {
    private final IngredientService ingredientService;
    private final RecipeService recipeService;

    public RecipeQueryService(IngredientService ingredientService, RecipeService recipeRepository) {
        this.ingredientService = ingredientService;
        this.recipeService = recipeRepository;
    }


    public List<Recipe> findAll(RecipeCriteria criteria) {
        Specification<Recipe> specification = createSpecification(criteria);
        return recipeService.findAll(specification);
    }


    public Recipe save(RecipeDTO recipeDTO) {
        Recipe recipe = mapFromDTO(recipeDTO);
        return recipeService.save(recipe);
    }


    private Specification<Recipe> createSpecification(RecipeCriteria criteria) {
        Specification<Recipe> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), "name"));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildSpecification(criteria.getType(), "type"));
            }
            if (criteria.getInstructions() != null) {
                specification = specification.and(buildSpecification(criteria.getInstructions(), "instructions"));
            }
            if (criteria.getNumberOfServings() != null) {
                specification = specification.and(buildSpecification(criteria.getNumberOfServings(), "numberOfServings"));
            }
        }
        return specification;
    }

    private Recipe mapFromDTO(RecipeDTO recipeDTO) {
        Recipe recipe = RecipeMapstructMapper.INSTANCE.mapDtoToRecipe(recipeDTO);

        List<Long> ingredientIds = recipeDTO.getRecipeIngredientDTOS().stream()
                .map(RecipeIngredientDTO::getIngredientId).collect(Collectors.toList());

        List<Ingredient> ingredients = ingredientService.findByIds(ingredientIds);
        if (ingredients.size() != recipeDTO.getRecipeIngredientDTOS().size()) {
            // TODO :: enhance the way to throw exception and display more handy message by catching it
            throw new RuntimeException("Wrong Ingredient Id added");
        }

        recipe.setRecipeIngredients(
                ingredients.stream().map(ingredient ->
                        RecipeIngredient.builder().ingredient(ingredient).recipe(recipe).amount(
                                findIngredientAmountFromRecipeDTO(recipeDTO, ingredient.getId())
                        ).build()
                ).collect(Collectors.toList())
        );
        return recipe;
    }

    private static Double findIngredientAmountFromRecipeDTO(RecipeDTO recipeDTO, Long ingredientId) {
        return recipeDTO.getRecipeIngredientDTOS().stream()
                .filter(re -> Objects.equals(re.getIngredientId(), ingredientId)).findFirst().map(RecipeIngredientDTO::getIngredientAmount)
                .orElse(null);
    }
}
