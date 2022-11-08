package com.abn.amro.recipes.service;

import com.abn.amro.recipes.mapper.RecipeMapper;
import com.abn.amro.recipes.model.Ingredient;
import com.abn.amro.recipes.model.Recipe;
import com.abn.amro.recipes.model.RecipeIngredient;
import com.abn.amro.recipes.model.RecipeType;
import com.abn.amro.recipes.model.criteria.RecipeCriteria;
import com.abn.amro.recipes.model.dto.RecipeDTO;
import com.abn.amro.recipes.model.dto.RecipeIngredientDTO;
import static com.abn.amro.recipes.utils.ErrorEnum.INGREDIENT_NOT_EXIST;
import static com.abn.amro.recipes.utils.ErrorEnum.RECIPE_TYPE_NOT_EXIST;
import static com.abn.amro.recipes.utils.ErrorUtils.generateInvalidInputError;
import static com.abn.amro.recipes.utils.ErrorUtils.generateNotFoundError;
import static com.abn.amro.recipes.utils.SpecificationUtils.buildDeepReferenceSpecification;
import static com.abn.amro.recipes.utils.SpecificationUtils.buildReferenceSpecification;
import static com.abn.amro.recipes.utils.SpecificationUtils.buildSpecification;
import static com.abn.amro.recipes.utils.SpecificationUtils.buildStringDeepReferenceSpecification;
import static com.abn.amro.recipes.utils.SpecificationUtils.buildStringReferenceSpecification;
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

    private final RecipeTypeService recipeTypeService;

    public RecipeQueryService(IngredientService ingredientService, RecipeService recipeRepository, RecipeTypeService recipeTypeService) {
        this.ingredientService = ingredientService;
        this.recipeService = recipeRepository;
        this.recipeTypeService = recipeTypeService;
    }


    public List<Recipe> findAll(RecipeCriteria criteria) {
        Specification<Recipe> specification = createSpecification(criteria);
        return recipeService.findAll(specification);
    }


    public Recipe save(RecipeDTO recipeDTO) {
        Recipe recipe = mapRecipeDtoToRecipe(recipeDTO);
        return recipeService.save(recipe);
    }

    public Recipe update(Long id, RecipeDTO recipeDTO) {
        Recipe savedRecipe = recipeService.findById(id);
        if (savedRecipe == null) {
            generateNotFoundError();
        }
        Recipe recipe = mapRecipeDtoToRecipe(recipeDTO);
        savedRecipe.setName(recipe.getName());
        savedRecipe.setInstructions(recipe.getInstructions());
        savedRecipe.setNumberOfServings(recipe.getNumberOfServings());
        savedRecipe.setRecipeType(recipe.getRecipeType());
        savedRecipe.setRecipeIngredients(recipe.getRecipeIngredients());
        return recipeService.save(savedRecipe);
    }


    private Specification<Recipe> createSpecification(RecipeCriteria criteria) {
        Specification<Recipe> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), "name"));
            }
            if (criteria.getInstructions() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInstructions(), "instructions"));
            }
            if (criteria.getNumberOfServings() != null) {
                specification = specification.and(buildSpecification(criteria.getNumberOfServings(), "numberOfServings"));
            }
            if (criteria.getRecipeTypeId() != null) {
                specification = specification.and(buildReferenceSpecification(criteria.getRecipeTypeId(), "recipeType", "id"));
            }
            if (criteria.getRecipeTypeName() != null) {
                specification = specification.and(buildStringReferenceSpecification(criteria.getRecipeTypeName(), "recipeType", "name"));
            }
            if (criteria.getIngredientId() != null) {// TODO :: enhance it by avoid joining ingredient table
                specification = specification.and(buildDeepReferenceSpecification(criteria.getIngredientId(), "recipeIngredients", "ingredient", "id"));
            }
            if (criteria.getIngredientName() != null) {
                specification = specification.and(buildStringDeepReferenceSpecification(criteria.getIngredientName(), "recipeIngredients", "ingredient", "name"));
            }
        }
        return specification;
    }

    private Recipe mapRecipeDtoToRecipe(RecipeDTO recipeDTO) {
        Recipe recipe = RecipeMapper.INSTANCE.mapDtoToRecipe(recipeDTO);

        RecipeType recipeType = recipeTypeService.findById(recipeDTO.getRecipeTypeId());
        if (recipeType == null) {
            generateInvalidInputError(RECIPE_TYPE_NOT_EXIST);
        }
        recipe.setRecipeType(recipeType);


        List<Long> ingredientIds = recipeDTO.getRecipeIngredientDTOS().stream()
                .map(RecipeIngredientDTO::getIngredientId).collect(Collectors.toList());

        List<Ingredient> ingredients = ingredientService.findByIds(ingredientIds);
        if (ingredients.size() != recipeDTO.getRecipeIngredientDTOS().size()) {
            generateInvalidInputError(INGREDIENT_NOT_EXIST);
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
