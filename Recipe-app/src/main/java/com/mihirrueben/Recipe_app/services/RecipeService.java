package com.mihirrueben.Recipe_app.services;

import com.mihirrueben.Recipe_app.model.Recipe;
import com.mihirrueben.Recipe_app.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;
    //Create or Update
    public Recipe saveRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    //read all
    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    //read by single id
    public Optional<Recipe> getRecipeById(String id) {
      return  recipeRepository.findById(id);
    }

    // Search Feature
    public List<Recipe> searchByTitle(String title) {
        return recipeRepository.findByTitleContainingIgnoreCase(title);
    }

    public Recipe updateRecipe(String id, Recipe updatedRecipe) {
        return recipeRepository.findById(id).map(recipe -> {
            recipe.setTitle(updatedRecipe.getTitle());
            recipe.setDescription(updatedRecipe.getDescription());
            recipe.setIngredients(updatedRecipe.getIngredients());
            recipe.setInstructions(updatedRecipe.getInstructions());
            recipe.setCategory(updatedRecipe.getCategory());
            recipe.setImageUrl(updatedRecipe.getImageUrl());

            return recipeRepository.save(recipe);
        }).orElseThrow(() -> new RuntimeException("Recipe not found with id " + id));
    }

    //Delete
    public void deleteRecipe(String id) {
        recipeRepository.findById(id).ifPresent(recipe -> {
            // 1. Delete the physical file if it exists
            if (recipe.getImageUrl() != null) {
                try {
                    // Convert "/uploads/name.jpg" to a local path
                    Path filePath = Paths.get(recipe.getImageUrl().substring(1));
                    Files.deleteIfExists(filePath);
                }catch (IOException e) {
                    System.out.println("Failed to delete image file: " + e.getMessage());
                }
            }
            // 2. Delete the record from MongoDB
            recipeRepository.deleteById(id);
        });
    }
}
