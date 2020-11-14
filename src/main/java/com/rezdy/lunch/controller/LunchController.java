package com.rezdy.lunch.controller;

import com.rezdy.lunch.model.Recipe;
import com.rezdy.lunch.service.LunchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/lunch")
public class LunchController {

    private final LunchService lunchService;

    public LunchController(final LunchService lunchService) {
        this.lunchService = lunchService;
    }

    @GetMapping
    public List<Recipe> getRecipes(@RequestParam(value = "date") String date) {
        return lunchService.getNonExpiredRecipesOnDate(LocalDate.parse(date));
    }
}
