package com.mihirrueben.Recipe_app.controller;

import com.mihirrueben.Recipe_app.model.Recipe;
import com.mihirrueben.Recipe_app.services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //for controller class to handle API requests
@RequestMapping("/api/recipes") //the "Base Url" for all endpoints
@CrossOrigin(origins = "*")

public class RecipeController {
    @Autowired
    private RecipeService recipeService;

    //creating a new recipe
    @PostMapping
    public ResponseEntity<Recipe> createRecipe(@RequestBody Recipe recipe) {
        return ResponseEntity.ok(recipeService.saveRecipe(recipe));
    }

    @GetMapping
    public List<Recipe> getAllRecipes() {
        return recipeService.getAllRecipes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable String id) {
        recipeService.deleteRecipe(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public List<Recipe> searchRecipes(@RequestParam String title) {
        return recipeService.searchByTitle(title);
    }

}

