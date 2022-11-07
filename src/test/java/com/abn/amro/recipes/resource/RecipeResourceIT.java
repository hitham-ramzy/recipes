package com.abn.amro.recipes.resource;

import com.abn.amro.recipes.model.Recipe;
import com.abn.amro.recipes.model.dto.RecipeDTO;
import com.abn.amro.recipes.repository.RecipeRepository;
import static com.abn.amro.recipes.utils.TestUtils.asJsonString;
import static com.abn.amro.recipes.utils.TestUtils.buildRandomRecipeDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RecipeResourceIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RecipeRepository recipeRepository;

    @Test
    @Order(1)
    public void getEmptyOffers() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                        get("/api/recipe")
                                .contentType("application/json")
                )
                .andExpect(status().isOk()).andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        List recipes = new ObjectMapper().readValue(json, List.class);
        assertThat(recipes.size()).isEqualTo(0);
    }

    @Order(2)
    @ParameterizedTest
    @MethodSource("provideValidRecipes")
    public void saveRecipe(RecipeDTO recipeDTO) throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                        post("/api/recipe")
                                .content(asJsonString(recipeDTO))
                                .contentType("application/json")
                )
                .andExpect(status().isOk()).andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        Recipe recipe = new ObjectMapper().readValue(json, Recipe.class);
        assertRecipe(recipeDTO, recipe);
    }

    private static List<RecipeDTO> provideValidRecipes() {
        return IntStream.rangeClosed(1, RandomUtils.nextInt(2, 10))
                .mapToObj(s -> buildRandomRecipeDTO())
                .collect(Collectors.toList());
    }


    private static void assertRecipe(RecipeDTO recipeDTO, Recipe recipe) {
        assertThat(recipeDTO.getName()).isEqualTo(recipe.getName());
        assertThat(recipeDTO.getRecipeTypeId()).isEqualTo(recipe.getRecipeType().getId());
        assertThat(recipeDTO.getInstructions()).isEqualTo(recipe.getInstructions());
        assertThat(recipeDTO.getNumberOfServings()).isEqualTo(recipe.getNumberOfServings());
        assertThat(recipeDTO.getRecipeIngredientDTOS().size()).isEqualTo(recipe.getRecipeIngredients().size());
    }

}