package com.aearost.recipebook.utils;

import com.aearost.recipebook.objects.Recipe;
import java.util.ArrayList;
import java.util.List;


public class RecipeUtils {
    
    private static List<Recipe> recipes = new ArrayList<>();
    
    public static List<Recipe> getRecipes() {
        return recipes;
    }
    
    public static void addRecipe(Recipe recipe) {
        recipes.add(recipe);
    }
    
}
