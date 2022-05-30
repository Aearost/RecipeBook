package com.aearost.recipebook.utils;

import com.aearost.recipebook.objects.Recipe;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;


public class RecipeUtils {
    
    private static List<Recipe> recipes = new ArrayList<>();
    private static Recipe loadedRecipe;
    
    public static List<Recipe> getRecipes() {
        return recipes;
    }
    
    public static void addRecipe(Recipe recipe) {
        recipes.add(recipe);
    }
    
    public static void deleteRecipe(Recipe recipe) {
        recipes.remove(recipe);
        RecipePersistence.deleteRecipeFile(recipe);
    }
    
    public static void setLoadedRecipe(Recipe recipe) {
        loadedRecipe = recipe;
    }
    
    public static Recipe getLoadedRecipe() {
        return loadedRecipe;
    }
    
    public static Recipe getRecipeByName(String recipeName) {
        for (Recipe recipe : getRecipes()) {
            if (recipe.getName().equals(recipeName)) {
                return recipe;
            }
        }
        // Will never happen as it only loads valid recipes
        Alert illegalInputAlert = new Alert(Alert.AlertType.ERROR);
        illegalInputAlert.setHeaderText("Hey!");
        illegalInputAlert.setContentText("In RecipeUtils.getRecipeByName(), it returned a null value!");
        illegalInputAlert.show();
        return null;
    }
    
    public static String getTextAreaStringFromList(List<String> list) {
        String line = "";
        for (int i = 0; i < list.size(); i++) {
            String ingredient = list.get(i);
            if (i == list.size() - 1) {
                line += ingredient;
            } else {
                line += ingredient + "\n";
            }
        }
        return line;
    }
    
    public static int getComboBoxIndexFromEnumString(ComboBox comboBox, String option) {
        for (int i = 0; i < comboBox.getItems().size(); i++) {
            String comboBoxOption = comboBox.getItems().get(i).toString();
            if (comboBoxOption.toUpperCase().equals(option.toUpperCase())) {
                return i;
            }
        }
        // Will never happen as it only loads valid meal types
        Alert illegalInputAlert = new Alert(Alert.AlertType.ERROR);
        illegalInputAlert.setHeaderText("Hey!");
        illegalInputAlert.setContentText("In RecipeUtils.getComboBoxIndexFromEnumString(), it returned a -1 value!");
        illegalInputAlert.show();
        return -1;
    }
    
}
