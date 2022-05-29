package com.aearost.recipebook.controllers;

import com.aearost.recipebook.App;
import com.aearost.recipebook.objects.Recipe;
import com.aearost.recipebook.utils.RecipeUtils;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.stage.Screen;

public class RecipeController {
    
    @FXML
    private Label nameLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    private TextArea ingredientsTextArea;
    @FXML
    private TextArea stepsTextArea;
    @FXML
    private Button recipeBackButton;
    @FXML
    private ImageView recipeImageView;
    
    
    @FXML
    public void onRecipeBackButtonClick() throws IOException {
        //App.setRoot("home");
        recipeBackButton.getScene().getWindow().hide();
    }
    
    @FXML
    public void initialize() {
        
        Recipe recipe = RecipeUtils.getLoadedRecipe();
        nameLabel.setText(recipe.getName());
        descriptionLabel.setText(recipe.getDescription());
        
        if (!recipe.getImageUrl().equals("")) {
            String url = "file:///" + recipe.getImageUrl();
            url = url.replace("\\", "/");
            Image image = new Image(url);
            recipeImageView.setImage(image);
            recipeImageView.setCache(true);
        } else {
            recipeImageView.setFitHeight(0);
        }
        
        String ingredients = "";
        for (int i = 0; i < recipe.getIngredients().size(); i++) {
            String ingredient = recipe.getIngredients().get(i);
            System.out.println(ingredient);
            if (i == recipe.getIngredients().size() - 1) {
                ingredients += ingredient;
            } else {
                ingredients += ingredient + "\n";
            }
        }
        ingredientsTextArea.setText(ingredients);
        ingredientsTextArea.setFont(Font.font("Lucida Bright", 16));
        ingredientsTextArea.setPrefWidth(Screen.getPrimary().getBounds().getWidth() * 0.35);
        if (recipe.getIngredients().size() < 9) {
            ingredientsTextArea.setPrefRowCount(recipe.getIngredients().size());
        } else {
            ingredientsTextArea.setPrefRowCount(9);
        }
        
        
        String steps = "";
        for (int i = 0; i < recipe.getSteps().size(); i++) {
            String step = recipe.getSteps().get(i);
            if (i == recipe.getSteps().size() - 1) {
                steps += step;
            } else {
                steps += step + "\n";
            }
        }
        stepsTextArea.setText(steps);
        stepsTextArea.setFont(Font.font("Lucida Bright", 16));
        stepsTextArea.setPrefWidth(Screen.getPrimary().getBounds().getWidth() * 0.5);
        if (recipe.getIngredients().size() < 9) {
            stepsTextArea.setPrefRowCount(recipe.getSteps().size());
        } else {
            stepsTextArea.setPrefRowCount(9);
        }
    }

}