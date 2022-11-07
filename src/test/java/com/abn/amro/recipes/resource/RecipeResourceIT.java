package com.abn.amro.recipes.resource;

import com.abn.amro.recipes.model.Recipe;
import com.abn.amro.recipes.model.dto.RecipeDTO;
import com.abn.amro.recipes.repository.RecipeRepository;
import static com.abn.amro.recipes.utils.TestUtils.asJsonString;
import static com.abn.amro.recipes.utils.TestUtils.buildRandomRecipeDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
/*
    @Test
    @Order(2)
    public void saveRecipe() throws Exception {
        RecipeDTO recipeDTO = buildRandomRecipeDTO();
        MvcResult mvcResult = mockMvc.perform(
                        post("/api/recipe")
                                .content(asJsonString(recipeDTO))
                                .contentType("application/json")
                )
                .andExpect(status().isOk()).andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        Recipe recipe = new ObjectMapper().readValue(json, Recipe.class);
        assertRecipe(recipeDTO, recipe);
    }*/


    private static void assertRecipe(RecipeDTO recipeDTO, Recipe recipe) {
        assertThat(recipeDTO.getName()).isEqualTo(recipe.getName());
    }

}