package com.abn.amro.recipes.resource;

import com.abn.amro.recipes.model.Ingredient;
import com.abn.amro.recipes.model.dto.IngredientDTO;
import com.abn.amro.recipes.repository.IngredientRepository;
import static com.abn.amro.recipes.utils.ErrorConstant.INGREDIENT_NAME_NOT_CHANGED;
import static com.abn.amro.recipes.utils.ErrorConstant.INGREDIENT_NOT_EXIST;
import static com.abn.amro.recipes.utils.ErrorConstant.NAME_ALREADY_EXIST;
import static com.abn.amro.recipes.utils.ErrorUtils.NAME_FIELD_LENGTH;
import static com.abn.amro.recipes.utils.TestUtils.INGREDIENTS_SEED_DATA_SIZE;
import static com.abn.amro.recipes.utils.TestUtils.asJsonString;
import static com.abn.amro.recipes.utils.TestUtils.buildRandomIngredientDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class IngredientResourceIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    IngredientRepository ingredientRepository;


    @Test
    @Order(1)
    public void getAllIngredients() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                        get("/api/ingredient")
                                .contentType("application/json")
                )
                .andExpect(status().isOk()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        List ingredients = new ObjectMapper().readValue(response, List.class);
        assertThat(ingredients.size()).isEqualTo(INGREDIENTS_SEED_DATA_SIZE);
    }


    @Test
    @Order(2)
    public void getIngredientById() throws Exception {
        Long id = 1L;
        MvcResult mvcResult = mockMvc.perform(
                        get("/api/ingredient/{id}", id)
                                .contentType("application/json")
                )
                .andExpect(status().isOk()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Ingredient ingredient = new ObjectMapper().readValue(response, Ingredient.class);
        Ingredient savedIngredient = ingredientRepository.getById(id);
        assertThat(savedIngredient.getName()).isEqualTo(ingredient.getName());
    }

    @Test
    @Order(3)
    public void getNotExitsIngredient() throws Exception {
        Long id = -1L;
        MvcResult mvcResult = mockMvc.perform(
                        get("/api/ingredient/{id}", id)
                                .contentType("application/json")
                )
                .andExpect(status().isOk()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        assertThat(response).isEmpty();
    }

    @Test
    @Order(4)
    public void saveIngredient() throws Exception {
        IngredientDTO ingredientDTO = buildRandomIngredientDTO(RandomStringUtils.randomAlphabetic(10));
        MvcResult mvcResult = mockMvc.perform(
                        post("/api/ingredient")
                                .content(asJsonString(ingredientDTO))
                                .contentType("application/json")
                )
                .andExpect(status().isOk()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Ingredient ingredient = new ObjectMapper().readValue(response, Ingredient.class);
        Ingredient savedIngredient = ingredientRepository.getById(ingredient.getId());
        assertThat(savedIngredient.getName()).isEqualTo(ingredientDTO.getName());
    }

    @Test
    @Order(5)
    public void saveTooLongIngredientName() throws Exception {
        IngredientDTO ingredientDTO = buildRandomIngredientDTO(RandomStringUtils.randomAlphabetic(100000));
        MvcResult mvcResult = mockMvc.perform(
                        post("/api/ingredient")
                                .content(asJsonString(ingredientDTO))
                                .contentType("application/json")
                )
                .andExpect(status().isBadRequest()).andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        assertThat(result).isEqualTo(NAME_FIELD_LENGTH);
    }

    @Test
    @Order(6)
    public void saveRepeatedIngredientName() throws Exception {
        Ingredient existIngredient = ingredientRepository.getById(1L);
        IngredientDTO ingredientDTO = buildRandomIngredientDTO(existIngredient.getName());
        MvcResult mvcResult = mockMvc.perform(
                        post("/api/ingredient")
                                .content(asJsonString(ingredientDTO))
                                .contentType("application/json")
                )
                .andExpect(status().isBadRequest()).andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        assertThat(result).isEqualTo(NAME_ALREADY_EXIST.getMessage());
    }

    @Test
    @Order(7)
    public void updateIngredientName() throws Exception {
        IngredientDTO ingredientDTO = buildRandomIngredientDTO(RandomStringUtils.randomAlphabetic(20));
        MvcResult mvcResult = mockMvc.perform(
                        put("/api/ingredient/{id}", 2L)
                                .content(asJsonString(ingredientDTO))
                                .contentType("application/json")
                )
                .andExpect(status().isOk()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        Ingredient ingredient = new ObjectMapper().readValue(response, Ingredient.class);
        Ingredient savedIngredient = ingredientRepository.getById(ingredient.getId());
        assertThat(savedIngredient.getName()).isEqualTo(ingredientDTO.getName());
    }

    @Test
    @Order(8)
    public void updateIngredientWithExistName() throws Exception {
        Ingredient existIngredient = ingredientRepository.getById(1L);
        IngredientDTO ingredientDTO = buildRandomIngredientDTO(existIngredient.getName());
        MvcResult mvcResult = mockMvc.perform(
                        put("/api/ingredient/{id}", 2L)
                                .content(asJsonString(ingredientDTO))
                                .contentType("application/json")
                )
                .andExpect(status().isBadRequest()).andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        assertThat(result).isEqualTo(NAME_ALREADY_EXIST.getMessage());
    }

    @Test
    @Order(9)
    public void updateIngredientWithTooLongName() throws Exception {
        IngredientDTO ingredientDTO = buildRandomIngredientDTO(RandomStringUtils.randomAlphabetic(1000));
        MvcResult mvcResult = mockMvc.perform(
                        put("/api/ingredient/{id}", 2L)
                                .content(asJsonString(ingredientDTO))
                                .contentType("application/json")
                )
                .andExpect(status().isBadRequest()).andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        assertThat(result).isEqualTo(NAME_FIELD_LENGTH);
    }

    @Test
    @Order(10)
    public void updateIngredientWithSameName() throws Exception {
        Ingredient existIngredient = ingredientRepository.getById(1L);
        MvcResult mvcResult = mockMvc.perform(
                        put("/api/ingredient/{id}", existIngredient.getId())
                                .content(asJsonString(buildRandomIngredientDTO(existIngredient.getName())))
                                .contentType("application/json")
                )
                .andExpect(status().isBadRequest()).andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        assertThat(result).isEqualTo(INGREDIENT_NAME_NOT_CHANGED.getMessage());
    }

    @Test
    @Order(11)
    public void updateNotExistIngredient() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                        put("/api/ingredient/{id}", -1L)
                                .content(asJsonString(buildRandomIngredientDTO(RandomStringUtils.randomAlphabetic(10))))
                                .contentType("application/json")
                )
                .andExpect(status().isBadRequest()).andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        assertThat(result).isEqualTo(INGREDIENT_NOT_EXIST.getMessage());
    }

    @Test
    @Order(12)
    public void deleteIngredient() throws Exception {
        Ingredient existIngredient = ingredientRepository.getById(1L);
        mockMvc.perform(
                        delete("/api/ingredient/{id}", existIngredient.getId())
                                .contentType("application/json")
                )
                .andExpect(status().isOk()).andReturn();
        assertThat(ingredientRepository.findById(1L).orElse(null)).isNull();
    }

    @Test
    @Order(13)
    public void deleteNotExistIngredient() throws Exception {
        Ingredient existIngredient = ingredientRepository.getById(-1L);
        MvcResult mvcResult = mockMvc.perform(
                        delete("/api/ingredient/{id}", existIngredient.getId())
                                .contentType("application/json")
                )
                .andExpect(status().isBadRequest()).andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        assertThat(result).isEqualTo(INGREDIENT_NOT_EXIST.getMessage());
    }

}