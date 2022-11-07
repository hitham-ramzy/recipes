package com.abn.amro.recipes.resource;

import com.abn.amro.recipes.model.RecipeType;
import com.abn.amro.recipes.model.dto.RecipeTypeDTO;
import com.abn.amro.recipes.repository.RecipeTypeRepository;
import static com.abn.amro.recipes.utils.ErrorConstant.NAME_ALREADY_EXIST;
import static com.abn.amro.recipes.utils.ErrorConstant.RECIPE_TYPE_NAME_NOT_CHANGED;
import static com.abn.amro.recipes.utils.ErrorConstant.RECIPE_TYPE_NOT_EXIST;
import static com.abn.amro.recipes.utils.ErrorUtils.NAME_FIELD_LENGTH;
import static com.abn.amro.recipes.utils.TestUtils.RECIPE_TYPE_SEED_DATA_SIZE;
import static com.abn.amro.recipes.utils.TestUtils.asJsonString;
import static com.abn.amro.recipes.utils.TestUtils.buildRandomRecipeTypeDTO;
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
class RecipeTypeResourceIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RecipeTypeRepository recipeTypeRepository;

    @Test
    @Order(1)
    public void getAllRecipeTypes() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                        get("/api/recipe-type")
                                .contentType("application/json")
                )
                .andExpect(status().isOk()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        List recipeTypes = new ObjectMapper().readValue(response, List.class);
        assertThat(recipeTypes.size()).isEqualTo(RECIPE_TYPE_SEED_DATA_SIZE);
    }


    @Test
    @Order(2)
    public void getRecipeTypeById() throws Exception {
        Long id = 1L;
        MvcResult mvcResult = mockMvc.perform(
                        get("/api/recipe-type/{id}", id)
                                .contentType("application/json")
                )
                .andExpect(status().isOk()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        RecipeType recipeType = new ObjectMapper().readValue(response, RecipeType.class);
        RecipeType savedRecipeType = recipeTypeRepository.getById(id);
        assertThat(savedRecipeType.getName()).isEqualTo(recipeType.getName());
    }

    @Test
    @Order(3)
    public void getNotExitsRecipeType() throws Exception {
        Long id = -1L;
        MvcResult mvcResult = mockMvc.perform(
                        get("/api/recipe-type/{id}", id)
                                .contentType("application/json")
                )
                .andExpect(status().isOk()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        assertThat(response).isEmpty();
    }

    @Test
    @Order(4)
    public void saveRecipeType() throws Exception {
        RecipeTypeDTO recipeTypeDTO = buildRandomRecipeTypeDTO(RandomStringUtils.randomAlphabetic(10));
        MvcResult mvcResult = mockMvc.perform(
                        post("/api/recipe-type")
                                .content(asJsonString(recipeTypeDTO))
                                .contentType("application/json")
                )
                .andExpect(status().isOk()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        RecipeType recipeType = new ObjectMapper().readValue(response, RecipeType.class);
        RecipeType savedRecipeType = recipeTypeRepository.getById(recipeType.getId());
        assertThat(savedRecipeType.getName()).isEqualTo(recipeTypeDTO.getName());
    }

    @Test
    @Order(5)
    public void saveTooLongRecipeTypeName() throws Exception {
        RecipeTypeDTO recipeTypeDTO = buildRandomRecipeTypeDTO(RandomStringUtils.randomAlphabetic(1000));
        MvcResult mvcResult = mockMvc.perform(
                        post("/api/recipe-type")
                                .content(asJsonString(recipeTypeDTO))
                                .contentType("application/json")
                )
                .andExpect(status().isBadRequest()).andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        assertThat(result).isEqualTo(NAME_FIELD_LENGTH);
    }

    @Test
    @Order(6)
    public void saveRepeatedRecipeTypeName() throws Exception {
        RecipeType existRecipeType = recipeTypeRepository.getById(1L);
        RecipeTypeDTO recipeTypeDTO = buildRandomRecipeTypeDTO(existRecipeType.getName());
        MvcResult mvcResult = mockMvc.perform(
                        post("/api/recipe-type")
                                .content(asJsonString(recipeTypeDTO))
                                .contentType("application/json")
                )
                .andExpect(status().isBadRequest()).andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        assertThat(result).isEqualTo(NAME_ALREADY_EXIST.getMessage());
    }

    @Test
    @Order(7)
    public void updateRecipeTypeName() throws Exception {
        RecipeTypeDTO recipeTypeDTO = buildRandomRecipeTypeDTO(RandomStringUtils.randomAlphabetic(20));
        MvcResult mvcResult = mockMvc.perform(
                        put("/api/recipe-type/{id}", 2L)
                                .content(asJsonString(recipeTypeDTO))
                                .contentType("application/json")
                )
                .andExpect(status().isOk()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        RecipeType recipeType = new ObjectMapper().readValue(response, RecipeType.class);
        RecipeType savedRecipeType = recipeTypeRepository.getById(recipeType.getId());
        assertThat(savedRecipeType.getName()).isEqualTo(recipeTypeDTO.getName());
    }

    @Test
    @Order(8)
    public void updateRecipeTypeWithExistName() throws Exception {
        RecipeType existRecipeType = recipeTypeRepository.getById(1L);
        RecipeTypeDTO recipeTypeDTO = buildRandomRecipeTypeDTO(existRecipeType.getName());
        MvcResult mvcResult = mockMvc.perform(
                        put("/api/recipe-type/{id}", 2L)
                                .content(asJsonString(recipeTypeDTO))
                                .contentType("application/json")
                )
                .andExpect(status().isBadRequest()).andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        assertThat(result).isEqualTo(NAME_ALREADY_EXIST.getMessage());
    }

    @Test
    @Order(9)
    public void updateRecipeTypeWithTooLongName() throws Exception {
        RecipeTypeDTO recipeTypeDTO = buildRandomRecipeTypeDTO(RandomStringUtils.randomAlphabetic(1000));
        MvcResult mvcResult = mockMvc.perform(
                        put("/api/recipe-type/{id}", 2L)
                                .content(asJsonString(recipeTypeDTO))
                                .contentType("application/json")
                )
                .andExpect(status().isBadRequest()).andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        assertThat(result).isEqualTo(NAME_FIELD_LENGTH);
    }

    @Test
    @Order(10)
    public void updateRecipeTypeWithSameName() throws Exception {
        RecipeType existRecipeType = recipeTypeRepository.getById(1L);
        MvcResult mvcResult = mockMvc.perform(
                        put("/api/recipe-type/{id}", existRecipeType.getId())
                                .content(asJsonString(buildRandomRecipeTypeDTO(existRecipeType.getName())))
                                .contentType("application/json")
                )
                .andExpect(status().isBadRequest()).andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        assertThat(result).isEqualTo(RECIPE_TYPE_NAME_NOT_CHANGED.getMessage());
    }

    @Test
    @Order(11)
    public void updateNotExistRecipeType() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                        put("/api/recipe-type/{id}", -1L)
                                .content(asJsonString(buildRandomRecipeTypeDTO(RandomStringUtils.randomAlphabetic(10))))
                                .contentType("application/json")
                )
                .andExpect(status().isBadRequest()).andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        assertThat(result).isEqualTo(RECIPE_TYPE_NOT_EXIST.getMessage());
    }

    @Test
    @Order(12)
    public void deleteRecipeType() throws Exception {
        RecipeType existRecipeType = recipeTypeRepository.getById(1L);
        mockMvc.perform(
                        delete("/api/recipe-type/{id}", existRecipeType.getId())
                                .contentType("application/json")
                )
                .andExpect(status().isOk()).andReturn();
        assertThat(recipeTypeRepository.findById(1L).orElse(null)).isNull();
    }

    @Test
    @Order(13)
    public void deleteNotExistRecipeType() throws Exception {
        RecipeType existRecipeType = recipeTypeRepository.getById(-1L);
        MvcResult mvcResult = mockMvc.perform(
                        delete("/api/recipe-type/{id}", existRecipeType.getId())
                                .contentType("application/json")
                )
                .andExpect(status().isBadRequest()).andReturn();
        String result = mvcResult.getResponse().getContentAsString();
        assertThat(result).isEqualTo(RECIPE_TYPE_NOT_EXIST.getMessage());
    }
}