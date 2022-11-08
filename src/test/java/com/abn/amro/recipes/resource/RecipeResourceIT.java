package com.abn.amro.recipes.resource;

import com.abn.amro.recipes.model.Recipe;
import com.abn.amro.recipes.model.dto.RecipeDTO;
import com.abn.amro.recipes.repository.RecipeRepository;
import com.abn.amro.recipes.service.IngredientService;
import static com.abn.amro.recipes.utils.ErrorEnum.INGREDIENT_NOT_EXIST;
import static com.abn.amro.recipes.utils.ErrorEnum.RECIPE_TYPE_NOT_EXIST;
import static com.abn.amro.recipes.utils.ErrorUtils.INGREDIENT_AMOUNT_NOT_NULL_MESSAGE;
import static com.abn.amro.recipes.utils.ErrorUtils.INGREDIENT_ID_NOT_NULL_MESSAGE;
import static com.abn.amro.recipes.utils.ErrorUtils.INSTRUCTIONS_LENGTH_MESSAGE;
import static com.abn.amro.recipes.utils.ErrorUtils.INSTRUCTIONS_NOT_NULL_MESSAGE;
import static com.abn.amro.recipes.utils.ErrorUtils.MEASUREMENT_UNIT_NOT_NULL_MESSAGE;
import static com.abn.amro.recipes.utils.ErrorUtils.NAME_LENGTH_MESSAGE;
import static com.abn.amro.recipes.utils.ErrorUtils.NAME_NOT_NULL_MESSAGE;
import static com.abn.amro.recipes.utils.ErrorUtils.NUM_OF_SERVINGS_MAX_MESSAGE;
import static com.abn.amro.recipes.utils.ErrorUtils.NUM_OF_SERVINGS_MIN_MESSAGE;
import static com.abn.amro.recipes.utils.ErrorUtils.NUM_OF_SERVINGS_NOT_NULL;
import static com.abn.amro.recipes.utils.ErrorUtils.RECIPE_INGREDIENTS_NOT_NULL;
import static com.abn.amro.recipes.utils.ErrorUtils.RECIPE_INGREDIENTS_SIZE;
import static com.abn.amro.recipes.utils.ErrorUtils.RECIPE_TYPE_ID_NOT_NULL_MESSAGE;
import static com.abn.amro.recipes.utils.TestUtils.RECIPE_SEED_DATA_SIZE;
import static com.abn.amro.recipes.utils.TestUtils.asJsonString;
import static com.abn.amro.recipes.utils.TestUtils.buildRandomListOfRecipeIngredientDTO;
import static com.abn.amro.recipes.utils.TestUtils.buildRandomMeasurementUnit;
import static com.abn.amro.recipes.utils.TestUtils.buildRandomRecipeDTO;
import static com.abn.amro.recipes.utils.TestUtils.buildRandomRecipeIngredientDTO;
import static com.abn.amro.recipes.utils.TestUtils.buildRecipeDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class RecipeResourceIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private IngredientService ingredientService;

    @Test
    public void getRecipes() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                        get("/api/recipe")
                                .contentType("application/json")
                )
                .andExpect(status().isOk()).andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        List recipes = new ObjectMapper().readValue(json, List.class);
        assertThat(recipes.size()).isEqualTo(RECIPE_SEED_DATA_SIZE);
    }

    @Test
    public void getRecipeById() throws Exception {
        Long id = 1L;
        MvcResult mvcResult = mockMvc.perform(
                        get("/api/recipe/{id}", id)
                                .contentType("application/json")
                )
                .andExpect(status().isOk()).andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        Recipe recipe = new ObjectMapper().readValue(json, Recipe.class);
        Recipe savedRecipe = recipeRepository.getById(id);
        assertThat(recipe).isEqualTo(savedRecipe);
    }

    @ParameterizedTest
    @MethodSource("provideValidRecipeDTOs")
    public void saveValidRecipes(RecipeDTO recipeDTO) throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                        post("/api/recipe")
                                .content(asJsonString(recipeDTO))
                                .contentType("application/json")
                )
                .andExpect(status().isOk()).andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        Recipe recipe = new ObjectMapper().readValue(json, Recipe.class);
        Recipe savedRecipe = recipeRepository.getById(recipe.getId());
        assertRecipe(recipeDTO, savedRecipe);
    }

    @ParameterizedTest
    @MethodSource("provideInvalidRecipeDTOs")
    public void saveInvalidRecipes(RecipeDTO recipeDTO, String errorMessage) throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                        post("/api/recipe")
                                .content(asJsonString(recipeDTO))
                                .contentType("application/json")
                )
                .andExpect(status().isBadRequest()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        assertThat(response).isEqualTo(errorMessage);
    }

    @Order(4)
    @ParameterizedTest
    @MethodSource("provideValidRecipeDTOs")
    public void updateValidRecipes(RecipeDTO recipeDTO) throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                        put("/api/recipe/{id}", 1L)
                                .content(asJsonString(recipeDTO))
                                .contentType("application/json")
                )
                .andExpect(status().isOk()).andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        Recipe recipe = new ObjectMapper().readValue(json, Recipe.class);
        Recipe savedRecipe = recipeRepository.getById(recipe.getId());
        assertRecipe(recipeDTO, savedRecipe);
    }

    @ParameterizedTest
    @MethodSource("provideInvalidRecipeDTOs")
    public void updateInvalidRecipes(RecipeDTO recipeDTO, String errorMessage) throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                        put("/api/recipe/{id}", 1L)
                                .content(asJsonString(recipeDTO))
                                .contentType("application/json")
                )
                .andExpect(status().isBadRequest()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        assertThat(response).isEqualTo(errorMessage);
    }


    @Test
    public void deleteRecipe() throws Exception {
        Long id = 1L;
        Recipe recipe = recipeRepository.getById(id);
        Long ingredientId = recipe.getRecipeIngredients().stream().findFirst()
                .map(recipeIngredient -> recipeIngredient.getIngredient().getId()).orElse(null);


        mockMvc.perform(
                        delete("/api/recipe/{id}", id)
                                .contentType("application/json")
                )
                .andExpect(status().isOk()).andReturn();
        assertThat(recipeRepository.findById(id).orElse(null)).isNull();
        assertThat(ingredientService.findById(ingredientId)).isNotNull();
    }

    private static List<RecipeDTO> provideValidRecipeDTOs() {
        return IntStream.rangeClosed(1, RandomUtils.nextInt(2, 10))
                .mapToObj(s -> buildRandomRecipeDTO())
                .collect(Collectors.toList());
    }

    private static Stream<Arguments> provideInvalidRecipeDTOs() {

        return Stream.of(
                Arguments.of(buildRecipeDTO(null,
                        RandomUtils.nextLong(1, 2), RandomStringUtils.randomAlphabetic(10),
                        RandomUtils.nextInt(1, 49), buildRandomListOfRecipeIngredientDTO()), NAME_NOT_NULL_MESSAGE),

                Arguments.of(buildRecipeDTO(RandomStringUtils.randomAlphabetic(101),
                        RandomUtils.nextLong(1, 2), RandomStringUtils.randomAlphabetic(10),
                        RandomUtils.nextInt(1, 49), buildRandomListOfRecipeIngredientDTO()), NAME_LENGTH_MESSAGE),

                Arguments.of(buildRecipeDTO(RandomStringUtils.randomAlphabetic(100),
                        null, RandomStringUtils.randomAlphabetic(10),
                        RandomUtils.nextInt(1, 49), buildRandomListOfRecipeIngredientDTO()), RECIPE_TYPE_ID_NOT_NULL_MESSAGE),

                Arguments.of(buildRecipeDTO(RandomStringUtils.randomAlphabetic(100),
                        3L, RandomStringUtils.randomAlphabetic(10),
                        50, Set.of(buildRandomRecipeIngredientDTO())), RECIPE_TYPE_NOT_EXIST.getMessage()),

                Arguments.of(buildRecipeDTO(RandomStringUtils.randomAlphabetic(100),
                        RandomUtils.nextLong(1, 2), RandomStringUtils.randomAlphabetic(9),
                        RandomUtils.nextInt(1, 49), buildRandomListOfRecipeIngredientDTO()), INSTRUCTIONS_LENGTH_MESSAGE),

                Arguments.of(buildRecipeDTO(RandomStringUtils.randomAlphabetic(100),
                        RandomUtils.nextLong(1, 2), null,
                        RandomUtils.nextInt(1, 49), buildRandomListOfRecipeIngredientDTO()), INSTRUCTIONS_NOT_NULL_MESSAGE),

                Arguments.of(buildRecipeDTO(RandomStringUtils.randomAlphabetic(100),
                        RandomUtils.nextLong(1, 2), RandomStringUtils.randomAlphabetic(10),
                        0, buildRandomListOfRecipeIngredientDTO()), NUM_OF_SERVINGS_MIN_MESSAGE),

                Arguments.of(buildRecipeDTO(RandomStringUtils.randomAlphabetic(100),
                        RandomUtils.nextLong(1, 2), RandomStringUtils.randomAlphabetic(10),
                        51, buildRandomListOfRecipeIngredientDTO()), NUM_OF_SERVINGS_MAX_MESSAGE),

                Arguments.of(buildRecipeDTO(RandomStringUtils.randomAlphabetic(100),
                        RandomUtils.nextLong(1, 2), RandomStringUtils.randomAlphabetic(10),
                        null, buildRandomListOfRecipeIngredientDTO()), NUM_OF_SERVINGS_NOT_NULL),

                Arguments.of(buildRecipeDTO(RandomStringUtils.randomAlphabetic(100),
                        RandomUtils.nextLong(1, 2), RandomStringUtils.randomAlphabetic(10),
                        50, null), RECIPE_INGREDIENTS_NOT_NULL),

                Arguments.of(buildRecipeDTO(RandomStringUtils.randomAlphabetic(100),
                        RandomUtils.nextLong(1, 2), RandomStringUtils.randomAlphabetic(10),
                        50, Set.of()), RECIPE_INGREDIENTS_SIZE),
                Arguments.of(buildRecipeDTO(RandomStringUtils.randomAlphabetic(100),
                        RandomUtils.nextLong(1, 2), RandomStringUtils.randomAlphabetic(10),
                        50, Set.of(buildRandomRecipeIngredientDTO(null,
                                buildRandomMeasurementUnit(),
                                RandomUtils.nextDouble()))), INGREDIENT_ID_NOT_NULL_MESSAGE),

                Arguments.of(buildRecipeDTO(RandomStringUtils.randomAlphabetic(100),
                        RandomUtils.nextLong(1, 2), RandomStringUtils.randomAlphabetic(10),
                        50, Set.of(buildRandomRecipeIngredientDTO(-1L,
                                buildRandomMeasurementUnit(),
                                RandomUtils.nextDouble()))), INGREDIENT_NOT_EXIST.getMessage()),

                Arguments.of(buildRecipeDTO(RandomStringUtils.randomAlphabetic(100),
                        RandomUtils.nextLong(1, 2), RandomStringUtils.randomAlphabetic(10),
                        50, Set.of(buildRandomRecipeIngredientDTO(RandomUtils.nextLong(1, 5),
                                null,
                                RandomUtils.nextDouble()))), MEASUREMENT_UNIT_NOT_NULL_MESSAGE),

                Arguments.of(buildRecipeDTO(RandomStringUtils.randomAlphabetic(100),
                        RandomUtils.nextLong(1, 2), RandomStringUtils.randomAlphabetic(10),
                        50, Set.of(buildRandomRecipeIngredientDTO(RandomUtils.nextLong(1, 5),
                                buildRandomMeasurementUnit(),
                                null))), INGREDIENT_AMOUNT_NOT_NULL_MESSAGE)
        );
    }


    private static void assertRecipe(RecipeDTO recipeDTO, Recipe recipe) {
        assertThat(recipeDTO.getName()).isEqualTo(recipe.getName());
        assertThat(recipeDTO.getRecipeTypeId()).isEqualTo(recipe.getRecipeType().getId());
        assertThat(recipeDTO.getInstructions()).isEqualTo(recipe.getInstructions());
        assertThat(recipeDTO.getNumberOfServings()).isEqualTo(recipe.getNumberOfServings());
        assertThat(recipeDTO.getRecipeIngredientDTOS().size()).isEqualTo(recipe.getRecipeIngredients().size());
    }

}