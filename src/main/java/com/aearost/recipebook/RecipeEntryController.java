package com.aearost.recipebook;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class RecipeEntryController {

    @FXML
    private TextField nameEntryTextField;
    @FXML
    private TextArea descriptionEntryTextArea;
    @FXML
    private TextArea ingredientsEntryTextArea;
    @FXML
    private TextArea stepsEntryTextArea;
    @FXML
    private ComboBox mealTypeEntryComboBox;
    @FXML
    private ComboBox cuisineEntryComboBox;
    @FXML
    private ComboBox costEntryComboBox;
    @FXML
    private TextField prepTimeEntryTextField;
    @FXML
    private TextField cookTimeEntryTextField;
    @FXML
    private ComboBox proteinTypeEntryComboBox;
    @FXML
    private Button recipeEntryBackButton;
    @FXML
    private Button recipeEntryAddRecipeButton;
    
    @FXML
    private void onRecipeEntryBackButtonClick() throws IOException {
        App.setRoot("home");
    }
    
    @FXML
    private void onRecipeEntryAddRecipeButtonClick() throws IOException {
        
        
        System.out.println("Do the validation!");
        
        
    }
    
    private void initializeSearchCriteria() {
        mealTypeEntryComboBox.getItems().removeAll(mealTypeEntryComboBox.getItems());
        mealTypeEntryComboBox.getItems().addAll("Breakfast", "Lunch", "Supper", "Sauce", "Snack", "Dessert");
        
        
        cuisineEntryComboBox.getItems().removeAll(cuisineEntryComboBox.getItems());
        cuisineEntryComboBox.getItems().addAll("American", "Chinese", "Greek", "Indian", "Italian", "Japanese", "Lebanese", "Mexican", "Thai");
        
        
        costEntryComboBox.getItems().removeAll(costEntryComboBox.getItems());
        costEntryComboBox.getItems().addAll("Less than $10", "Between $10 and $15", "Between $15 and $20", "Between $20 and $30", "More than $30");
        
        
        proteinTypeEntryComboBox.getItems().removeAll(proteinTypeEntryComboBox.getItems());
        proteinTypeEntryComboBox.getItems().addAll("Chicken", "Beef", "Pork", "Fish", "Tofu", "Vegeterian", "Dessert");
    }
    
    @FXML
    public void initialize() {
        initializeSearchCriteria();
    }
}