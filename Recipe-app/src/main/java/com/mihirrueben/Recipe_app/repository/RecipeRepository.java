package com.mihirrueben.Recipe_app.repository;

import com.mihirrueben.Recipe_app.model.Recipe;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RecipeRepository extends MongoRepository<Recipe, String> {

    //Finding all recipes created by a specific user
    List<Recipe> findByUserId(String userId);

    //Search recipes by title (case-insensitive)
    List<Recipe> findByTitleContainingIgnoreCase(String title);

    //Filter recipes by category
    List<Recipe> findByCategory(String category);
}