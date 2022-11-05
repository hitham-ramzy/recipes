package com.abn.amro.recipes.service;

import com.abn.amro.recipes.mapper.RecipeMapper;
import com.abn.amro.recipes.model.Ingredient;
import com.abn.amro.recipes.model.Recipe;
import com.abn.amro.recipes.model.RecipeIngredient;
import com.abn.amro.recipes.model.criteria.Filter;
import com.abn.amro.recipes.model.criteria.RecipeCriteria;
import com.abn.amro.recipes.model.criteria.StringFilter;
import com.abn.amro.recipes.model.dto.RecipeDTO;
import com.abn.amro.recipes.model.dto.RecipeIngredientDTO;
import com.abn.amro.recipes.repository.RecipeRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final IngredientService ingredientService;

    public RecipeService(RecipeRepository recipeRepository, IngredientService ingredientService) {
        this.recipeRepository = recipeRepository;
        this.ingredientService = ingredientService;
    }

    public Recipe save(RecipeDTO recipeDTO) {
        Recipe recipe = mapFromDTO(recipeDTO);
        return recipeRepository.save(recipe);
    }

    public Recipe findById(Long id) {
        return recipeRepository.findById(id).orElse(null);
    }

    public List<Recipe> findAll(RecipeCriteria criteria) {
        Specification<Recipe> specification = createSpecification(criteria);
        return recipeRepository.findAll(specification);
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


    protected Specification<Recipe> buildSpecification(Filter filter, String fieldName) {
        if (filter.getEquals() != null) {
            return (root, query, builder) -> builder.equal(root.get(fieldName), filter.getEquals());
        } else if (filter.getNotEquals() != null) {
            return (root, query, builder) -> builder.notEqual(root.get(fieldName), filter.getNotEquals());
        }
        return null;
    }


    private Specification<Recipe> buildStringSpecification(StringFilter filter, String fieldName) {
        Specification<Recipe> specification = buildSpecification(filter, fieldName);
        if (specification == null && filter.getContains() != null) {
            return (root, query, builder) -> builder.like(builder.upper(root.get(fieldName)), this.wrapLikeQuery(filter.getContains()));
        }
        return specification;
    }

    private String wrapLikeQuery(String txt) {
        return "%" + txt.toUpperCase() + '%';
    }


    // TODO :: think about move the mapping into different layer, as the service layer should not depend on the DTOs
    private Recipe mapFromDTO(RecipeDTO recipeDTO) {
        Recipe recipe = RecipeMapper.INSTANCE.mapDtoToRecipe(recipeDTO);

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

//    private static maptToRecip
}
