package com.aearost.recipebook.controllers;

import com.aearost.recipebook.App;
import com.aearost.recipebook.objects.Cost;
import com.aearost.recipebook.objects.Cuisine;
import com.aearost.recipebook.utils.RecipeUtils;
import com.aearost.recipebook.objects.MealType;
import com.aearost.recipebook.objects.ProteinType;
import com.aearost.recipebook.objects.Recipe;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.stage.Screen;

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
        
        MealType mealTypeSelected = MealType.valueOf(mealTypeComboBox.getSelectionModel().getSelectedItem().toString().toUpperCase());
        Cuisine cuisineSelected = Cuisine.valueOf(cuisineComboBox.getSelectionModel().getSelectedItem().toString().toUpperCase());

        String costSelectedString = costComboBox.getSelectionModel().getSelectedItem().toString();
        Cost costSelected;
        switch (costSelectedString) {
            case "Less than $10":
                costSelected = Cost.LT10;
                break;
            case "Between $10 and $15":
                costSelected = Cost.LT15;
                break;
            case "Between $15 and $20":
                costSelected = Cost.LT20;
                break;
            case "Between $20 and $30":
                costSelected = Cost.LT30;
                break;
            case "More than $30":
                costSelected = Cost.GT30;
                break;
            default:
                costSelected = Cost.ANY;
                break;
        }
        
        String totalTimeSelectedString = totalTimeComboBox.getSelectionModel().getSelectedItem().toString();
        int totalTimeSelected = 0;
        switch (totalTimeSelectedString) {
            case "Less than 10 mins":
                totalTimeSelected = 10;
                break;
            case "Less than 30 mins":
                totalTimeSelected = 30;
                break;
            case "Less than 1 hour":
                totalTimeSelected = 60;
                break;
            case "Less than 2 hours":
                totalTimeSelected = 120;
                break;
            case "More than 2 hours":
                // 1 full week
                totalTimeSelected = 10080;
                break;
            default:
                totalTimeSelected = -1;
                break;
        }
        
        ProteinType proteinTypeSelected = ProteinType.valueOf(proteinTypeComboBox.getSelectionModel().getSelectedItem().toString().toUpperCase());
        
        for (Recipe recipe : RecipeUtils.getRecipes()) {
            
            // Filter based on search criteria
            if (mealTypeSelected != MealType.ANY) {
                if (recipe.getMealType() != mealTypeSelected) {
                    continue;
                }
            }
            if (cuisineSelected != Cuisine.ANY) {
                if (recipe.getCuisine() != cuisineSelected) {
                    continue;
                }
            }
            if (costSelected != Cost.ANY) {
                if (recipe.getCost() != costSelected) {
                    continue;
                }
            }
            if (totalTimeSelected != -1) {
                int recipeTotalTime = recipe.getPrepTime() + recipe.getCookTime();
                if (recipeTotalTime > totalTimeSelected) {
                    continue;
                }
            }
            if (proteinTypeSelected != ProteinType.ANY) {
                if (recipe.getProteinType() != proteinTypeSelected) {
                    continue;
                }
            }
            
            HBox row = new HBox();
            row.setAlignment(Pos.CENTER);
            row.setPadding(new Insets(25));
            row.setSpacing(Screen.getPrimary().getBounds().getWidth() * 0.05);
            
            if (!recipe.getImageUrl().equals("")) {
                String url = "file:///" + recipe.getImageUrl();
                url = url.replace("\\", "/");
                Image image = new Image(url);
                ImageView imageView = new ImageView();
                imageView.setImage(image);
                imageView.setFitWidth(150);
                imageView.setPreserveRatio(true);
                imageView.setSmooth(true);
                imageView.setCache(true);
                row.getChildren().add(imageView);
            }
            
            HBox content = new HBox();
            content.setPrefWidth(Screen.getPrimary().getBounds().getWidth() * 0.4);
            content.setMaxWidth(Screen.getPrimary().getBounds().getWidth() * 0.4);
            content.setAlignment(Pos.CENTER);
            content.setSpacing(200);
            
            
            
            Text name = new Text();
            name.setText(recipe.getName());
            name.setFont(Font.font("Lucida Bright Demibold", 20));
            name.setUnderline(true);

            Text totalTime = new Text();
            totalTime.setText("Total Time: " + (recipe.getPrepTime() + recipe.getCookTime()));
            totalTime.setFont(Font.font("Lucida Bright Demibold", 20));

            Text description = new Text();
            description.setText(recipe.getDescription());
            description.setFont(Font.font("Lucida Bright Demibold", FontPosture.ITALIC, 20));
            description.setWrappingWidth(content.getMaxWidth() * 0.5);
            

            VBox stats = new VBox();
            stats.setSpacing(50);
            stats.getChildren().addAll(name, totalTime);
            VBox desc = new VBox();
            desc.getChildren().addAll(description);

            
            content.getChildren().addAll(stats, desc);
            row.getChildren().add(content);
            recipeResultsListView.getItems().add(row);
        }
        if (recipeResultsListView.getItems().isEmpty()) {
            Text noResultsText = new Text();
            noResultsText.setText("There are no results with this search criteria!");
            noResultsText.setFont(Font.font("Lucida Bright Demibold", FontPosture.ITALIC, 20));
            
            HBox noResultsHBox = new HBox();
            noResultsHBox.setAlignment(Pos.CENTER);
            noResultsHBox.getChildren().add(noResultsText);
            recipeResultsListView.getItems().add(noResultsHBox);
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
        proteinTypeComboBox.getItems().addAll("Any", "Chicken", "Beef", "Pork", "Fish", "Tofu", "Vegetarian", "Dessert");
        proteinTypeComboBox.getSelectionModel().select("Any");
    }
    
    @FXML
    public void initialize() {
        initializeSearchCriteria();
    }
    
    
}
