package com.rezdy.lunch.controller;

import com.rezdy.lunch.service.LunchService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;

import static com.rezdy.lunch.helper.RecipeTestDataHelper.createTestRecipes;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = LunchController.class)
class LunchControllerTest {
    private static final LocalDate DATE = LocalDate.parse("2020-01-01");
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LunchService lunchService;

    @Test
    void should_return_internal_server_error_when_parameter_cannot_be_parsed_to_local_date() throws Exception {
        mockMvc.perform(get("/lunch").param("date", "test"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void should_return_empty_list_when_there_is_no_recipe() throws Exception {
        when(lunchService.getNonExpiredRecipesOnDate(DATE)).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/lunch").param("date", DATE.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(0));
    }

    @Test
    void should_return_recipe_list() throws Exception {
        when(lunchService.getNonExpiredRecipesOnDate(DATE)).thenReturn(createTestRecipes());
        mockMvc.perform(get("/lunch").param("date", DATE.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(4))
                .andExpect(jsonPath("$[0].title").value("recipe1"))
                .andExpect(jsonPath("$[0].ingredients.size()").value(2))
                .andExpect(jsonPath("$[1].title").value("recipe2"))
                .andExpect(jsonPath("$[1].ingredients.size()").value(2))
                .andExpect(jsonPath("$[2].title").value("recipe3"))
                .andExpect(jsonPath("$[2].ingredients.size()").value(2))
                .andExpect(jsonPath("$[3].title").value("recipe4"))
                .andExpect(jsonPath("$[3].ingredients.size()").value(3));
    }
}