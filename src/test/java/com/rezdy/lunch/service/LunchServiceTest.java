package com.rezdy.lunch.service;

import com.rezdy.lunch.model.Recipe;
import com.rezdy.lunch.repository.LunchDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static com.rezdy.lunch.helper.RecipeTestDataHelper.createTestRecipes;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LunchServiceTest {
    @Mock
    private LunchDao lunchDao;

    private LunchService service;

    @BeforeEach
    void setUp() {
        service = new LunchService(lunchDao);
        when(lunchDao.loadRecipes(any())).thenReturn(createTestRecipes());
    }

    @Test
    void should_put_one_recipe_last_when_best_before_date_past_given_date_in_one_recipe() {
        List<Recipe> recipeList = service.getNonExpiredRecipesOnDate(LocalDate.parse("2010-01-01"));
        assertEquals(4, recipeList.size());
        assertEquals("recipe2", recipeList.get(0).getTitle());
        assertEquals("recipe3", recipeList.get(1).getTitle());
        assertEquals("recipe4", recipeList.get(2).getTitle());
        assertEquals("recipe1", recipeList.get(3).getTitle());
    }

    @Test
    void should_put_two_recipe_last_sorted_when_best_before_date_past_given_date_in_two_recipes() {
        List<Recipe> recipeList = service.getNonExpiredRecipesOnDate(LocalDate.parse("2020-01-01"));
        assertEquals(4, recipeList.size());
        assertEquals("recipe3", recipeList.get(0).getTitle());
        assertEquals("recipe4", recipeList.get(1).getTitle());
        assertEquals("recipe1", recipeList.get(2).getTitle());
        assertEquals("recipe2", recipeList.get(3).getTitle());
    }

    @Test
    void should_return_unsorted_recipes_when_there_is_no_best_before_past_ingredient_on_each_recipe() {
        List<Recipe> recipeList = service.getNonExpiredRecipesOnDate(LocalDate.parse("1990-01-01"));
        assertEquals(4, recipeList.size());
        assertEquals("recipe1", recipeList.get(0).getTitle());
        assertEquals("recipe2", recipeList.get(1).getTitle());
        assertEquals("recipe3", recipeList.get(2).getTitle());
        assertEquals("recipe4", recipeList.get(3).getTitle());
    }

    @Test
    void should_return_empty_list_when_there_is_no_recipe() {
        when(lunchDao.loadRecipes(any())).thenReturn(Collections.emptyList());
        List<Recipe> recipeList = service.getNonExpiredRecipesOnDate(LocalDate.parse("1990-01-01"));
        assertEquals(0, recipeList.size());
    }

}