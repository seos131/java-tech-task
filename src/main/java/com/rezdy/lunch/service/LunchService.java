package com.rezdy.lunch.service;

import com.rezdy.lunch.model.Recipe;
import com.rezdy.lunch.repository.LunchDao;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LunchService {
    private final LunchDao lunchDao;

    public LunchService(final LunchDao lunchDao) {
        this.lunchDao = lunchDao;
    }

    public List<Recipe> getNonExpiredRecipesOnDate(LocalDate date) {
        // compare each recipe from database. If the recipe has any ingredient past best before, then put it into last
        return lunchDao.loadRecipes(date)
                .stream()
                .sorted((r1, r2) -> isRecipeContainingIngredientPastBestBefore(r1, date) ? 1 : isRecipeContainingIngredientPastBestBefore(r2, date) ? -1: 0)
                .collect(Collectors.toList());
    }

    private boolean isRecipeContainingIngredientPastBestBefore(Recipe recipe, LocalDate date) {
        return Optional.ofNullable(recipe.getIngredients()).orElse(new HashSet<>()).stream()
                .anyMatch(ingredient -> ingredient.getBestBefore().compareTo(date) < 0);

    }
}
