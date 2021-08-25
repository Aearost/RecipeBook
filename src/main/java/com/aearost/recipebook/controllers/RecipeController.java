package com.aearost.recipebook.controllers;

import com.aearost.recipebook.App;
import com.aearost.recipebook.objects.Recipe;
import com.aearost.recipebook.utils.RecipeUtils;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class RecipeController {
    
    @FXML
    private Label nameLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    private Button recipeBackButton;
    
    
    @FXML
    public void onRecipeBackButtonClick() throws IOException {
        App.setRoot("home");
    }
    
    @FXML
    public void initialize() {
        
        Recipe recipe = RecipeUtils.getLoadedRecipe();
        nameLabel.setText(recipe.getName());
        descriptionLabel.setText(recipe.getDescription());
        
        
    }

}