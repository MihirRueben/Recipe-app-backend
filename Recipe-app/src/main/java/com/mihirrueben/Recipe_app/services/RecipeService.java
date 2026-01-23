package com.mihirrueben.Recipe_app.services;

import com.mihirrueben.Recipe_app.model.Recipe;
import com.mihirrueben.Recipe_app.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    //Delete
    public void deleteRecipe(String id) {
        recipeRepository.deleteById(id);
    }

    // Search Feature
    public List<Recipe> searchByTitle(String title) {
        return recipeRepository.findByTitleContainingIgnoreCase(title);
    }


}
