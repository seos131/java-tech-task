package com.rezdy.lunch.helper;

import com.rezdy.lunch.model.Ingredient;
import com.rezdy.lunch.model.Recipe;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class RecipeTestDataHelper {
    public static List<Recipe> createTestRecipes() {
        Recipe recipe1 = createRecipe(
                Set.of(
                        createIngredient(LocalDate.parse("2000-01-01")),
                        createIngredient(LocalDate.parse("2010-11-01"))
                ),
                "recipe1"
        );

        Recipe recipe2 = createRecipe(
                Set.of(
                        createIngredient(LocalDate.parse("2010-01-01")),
                        createIngredient(LocalDate.parse("2030-11-01"))
                ),
                "recipe2"
        );

        Recipe recipe3 = createRecipe(
                Set.of(
                        createIngredient(LocalDate.parse("2030-01-01")),
                        createIngredient(LocalDate.parse("2020-09-01"))
                ),
                "recipe3"
        );

        Recipe recipe4 = createRecipe(
                Set.of(
                        createIngredient(LocalDate.parse("2030-01-01")),
                        createIngredient(LocalDate.parse("2030-09-01")),
                        createIngredient(LocalDate.parse("2020-10-01"))
                ),
                "recipe4"
        );

        return Arrays.asList(recipe1, recipe2, recipe3, recipe4);
    }

    private static Recipe createRecipe(Set<Ingredient> ingredients, String recipeTitle) {
        Recipe recipe = new Recipe();
        recipe.setIngredients(ingredients);
        recipe.setTitle(recipeTitle);
        return recipe;
    }

    private static Ingredient createIngredient(LocalDate bestBefore) {
        Ingredient ingredient = new Ingredient();
        ingredient.setBestBefore(bestBefore);
        return ingredient;
    }
}
