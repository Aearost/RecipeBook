package com.aearost.recipebook;

import com.aearost.recipebook.objects.Recipe;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;

public class HomeController {

    @FXML
    private ComboBox mealTypeComboBox;
    @FXML
    private ComboBox cuisineComboBox;
    @FXML
    private ComboBox costComboBox;
    @FXML
    private ComboBox totalTimeComboBox;
    @FXML
    private ComboBox proteinTypeComboBox;
    @FXML
    private Button addRecipeButton;
    @FXML
    private Button recipeSearchButton;
    @FXML
    private ListView recipeResultsListView;
    
    
    @FXML
    private void onRecipeSearchButtonClick() {
        recipeResultsListView.getItems().clear();
        for (Recipe recipe : RecipeUtils.getRecipes()) {
            recipeResultsListView.getItems().add(recipe.getName());
        }
    }
    
    @FXML
    private void onAddRecipeButtonClick() throws IOException {
        App.setRoot("recipe-entry");
    }
    
    private void initializeSearchCriteria() {
        mealTypeComboBox.getItems().removeAll(mealTypeComboBox.getItems());
        mealTypeComboBox.getItems().addAll("Any", "Breakfast", "Lunch", "Supper", "Sauce", "Snack", "Dessert");
        mealTypeComboBox.getSelectionModel().select("Any");
        
        
        cuisineComboBox.getItems().removeAll(cuisineComboBox.getItems());
        cuisineComboBox.getItems().addAll("Any", "American", "Chinese", "Greek", "Indian", "Italian", "Japanese", "Lebanese", "Mexican", "Thai");
        cuisineComboBox.getSelectionModel().select("Any");
        
        
        costComboBox.getItems().removeAll(costComboBox.getItems());
        costComboBox.getItems().addAll("Any", "Less than $10", "Between $10 and $15", "Between $15 and $20", "Between $20 and $30", "More than $30");
        costComboBox.getSelectionModel().select("Any");
        
        
        totalTimeComboBox.getItems().removeAll(totalTimeComboBox.getItems());
        totalTimeComboBox.getItems().addAll("Any", "Less than 10 mins", "Less than 30 mins", "Less than 1 hour", "Less than 2 hours", "More than 2 hours");
        totalTimeComboBox.getSelectionModel().select("Any");
        
        
        proteinTypeComboBox.getItems().removeAll(proteinTypeComboBox.getItems());
        proteinTypeComboBox.getItems().addAll("Any", "Chicken", "Beef", "Pork", "Fish", "Tofu", "Vegeterian", "Dessert");
        proteinTypeComboBox.getSelectionModel().select("Any");
    }
    
    @FXML
    public void initialize() {
        initializeSearchCriteria();
    }
    
    
}
