package com.mihirrueben.Recipe_app.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import java.util.List;

@Data
@Document(collection = "recipes")
public class Recipe {
    @Id
    private String id;
    private String title;
    private String description;

    // Using List<String> is perfect for MongoDB's flexible schema
    private List<String> ingredients;
    private String instructions;

    // For Bonus Features [cite: 16, 17]
    private String category; // e.g., Vegan, Dessert
    private String imageUrl; // Path to the uploaded photo

    // Links this recipe to the specific User who created it
    private String userId;
}