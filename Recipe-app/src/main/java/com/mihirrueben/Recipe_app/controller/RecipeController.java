package com.mihirrueben.Recipe_app.controller;

import com.mihirrueben.Recipe_app.model.Recipe;
import com.mihirrueben.Recipe_app.services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //for controller class to handle API requests
@RequestMapping("/api/recipes") //the "Base Url" for all endpoints
@CrossOrigin(origins = "*")


public class RecipeController {
    @Autowired
    private RecipeService recipeService;

    @Autowired
    private org.springframework.data.mongodb.core.MongoTemplate mongoTemplate;

    //creating a new recipe
    @PostMapping
    public ResponseEntity<Recipe> createRecipe(@RequestBody Recipe recipe) {
        return ResponseEntity.ok(recipeService.saveRecipe(recipe));
    }

   //  Get all
   @GetMapping
   public List<Recipe> getAllRecipes() {
       return recipeService.getAllRecipes();
   }

    //3. Search
    @GetMapping("/search")
    public List<Recipe> searchRecipes(@RequestParam String title) {
        return recipeService.searchByTitle(title);
    }


    //GET ONE RECIPE by id
    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable String id) {
        return recipeService.getRecipeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Recipe> updateRecipe(@PathVariable String id, @RequestBody Recipe recipe) {
        try {
            return ResponseEntity.ok(recipeService.updateRecipe(id, recipe));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    //6. Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable String id) {
        recipeService.deleteRecipe(id);
        return ResponseEntity.noContent().build();
    }

}

