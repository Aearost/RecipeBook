package com.aearost.recipebook.controllers;

import com.aearost.recipebook.App;
import com.aearost.recipebook.utils.RecipePersistence;
import com.aearost.recipebook.utils.RecipeUtils;
import com.aearost.recipebook.objects.Cost;
import com.aearost.recipebook.objects.Cuisine;
import com.aearost.recipebook.objects.MealType;
import com.aearost.recipebook.objects.ProteinType;
import com.aearost.recipebook.objects.Recipe;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

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
    private Button imageAttachButton;
    @FXML
    private Text imagePathText;
    @FXML
    private Button recipeEntryBackButton;
    @FXML
    private Button recipeEntryAddRecipeButton;
    
    private boolean hasIllegalCharacterInput = false;
    
    @FXML
    private void onRecipeEntryBackButtonClick() throws IOException {
        App.setRoot("home");
    }
    
    @FXML
    private void onImageAttachButtonClick() {
        FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            fileChooser.getExtensionFilters().addAll(
                    new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
            File selectedFile = fileChooser.showOpenDialog(imageAttachButton.getScene().getWindow());
            
            imagePathText.setText(selectedFile.getPath());
    }
    
    @FXML
    private void onRecipeEntryAddRecipeButtonClick() throws IOException {
        
        String name = nameEntryTextField.getText();
        String description = descriptionEntryTextArea.getText();
        List<String> ingredients = new ArrayList<>();
        ingredients.addAll(Arrays.asList(ingredientsEntryTextArea.getText().split("\n")));
        
        List<String> steps = new ArrayList<>();
        steps.addAll(Arrays.asList(stepsEntryTextArea.getText().split("\n")));
        
        // Note that imageUrl is optional so it does not need to be validated
        boolean isFieldsValidated = validateFields(name, description, ingredients, steps);
        boolean isNumberFieldsValidated = validateNumberFields(prepTimeEntryTextField.getText(), cookTimeEntryTextField.getText());
        boolean isEnumFieldsValidated = validateEnumFields();
        
        if (isFieldsValidated && isNumberFieldsValidated && isEnumFieldsValidated) {
            // Initialize the number fields
            int prepTime = Integer.parseInt(prepTimeEntryTextField.getText());
            int cookTime = Integer.parseInt(cookTimeEntryTextField.getText());
            
            //Initialize the enum fields
            MealType mealType = MealType.valueOf(mealTypeEntryComboBox.getSelectionModel().getSelectedItem().toString().toUpperCase());
            Cuisine cuisine = Cuisine.valueOf(cuisineEntryComboBox.getSelectionModel().getSelectedItem().toString().toUpperCase());

            Cost cost;
            String costContent = costEntryComboBox.getSelectionModel().getSelectedItem().toString();
            switch (costContent) {
                case "Between $10 and $15":
                    cost = Cost.LT15;
                    break;
                case "Between $15 and $20":
                    cost = Cost.LT20;
                    break;
                case "Between $20 and $30":
                    cost = Cost.LT30;
                    break;
                case "More than $30":
                    cost = Cost.GT30;
                    break;
                default:
                    cost = Cost.LT10;
                    break;
            }

            ProteinType proteinType = ProteinType.valueOf(proteinTypeEntryComboBox.getSelectionModel().getSelectedItem().toString().toUpperCase());
            
            // No image was selected
            if (imagePathText.getText().equals("")) {
                Recipe recipe = new Recipe(name, description, ingredients, steps, mealType, cuisine, cost, prepTime, cookTime, proteinType);
                RecipePersistence.writeRecipeToFile(recipe);
            }
            // An image was selected
            else {
                Recipe recipe = new Recipe(name, description, ingredients, steps, mealType, cuisine, cost, prepTime, cookTime, proteinType, imagePathText.getText());
                RecipePersistence.writeRecipeToFile(recipe);
                RecipeUtils.addRecipe(recipe);
            }
        } else {
            if (hasIllegalCharacterInput) {
                Alert illegalInputAlert = new Alert(AlertType.INFORMATION);
                illegalInputAlert.setHeaderText("Hey!");
                illegalInputAlert.setContentText("You cannot use an illegal character as input such as \\, \", etc.");
                illegalInputAlert.show();
            } else {
                Alert invalidFieldsAlert = new Alert(AlertType.INFORMATION);
                invalidFieldsAlert.setHeaderText("Hey!");
                invalidFieldsAlert.setContentText("One or more fields is filled incorrectly.");
                invalidFieldsAlert.show();
            }
        }
        
    }
    
    
    private boolean validateFields(String name, String description, List<String> ingredients, List<String> steps) {
        int invalidCount = 0;
        if (hasIllegalCharacters(name)) {
            invalidCount++;
        }
        if (hasIllegalCharacters(description)) {
            invalidCount++;
        }
        for (String ingredient : ingredients) {
            if (hasIllegalCharacters(ingredient)) {
                invalidCount++;
            }
        }
        for (String step : steps) {
            if (hasIllegalCharacters(step)) {
                invalidCount++;
            }
        }
        if (invalidCount > 0) {
            return false;
        }
        
        if (name.length() < 1) {
            return false;
        }
        if (description.length() < 1) {
            return false;
        }
        if (ingredients.size() < 1) {
            return false;
        }
        if (steps.size() < 1) {
            return false;
        }
        return true;
    }
    
    
    private boolean validateNumberFields(String prepTimeString, String cookTimeString) {
        int invalidCount = 0;
        
        if (hasIllegalCharacters(prepTimeString)) {
            invalidCount++;
        }
        if (hasIllegalCharacters(cookTimeString)) {
            invalidCount++;
        }
        if (invalidCount > 0) {
            return false;
        }
        
        int prepTime;
        int cookTime;
        try {
            prepTime = Integer.parseInt(prepTimeString);
            cookTime = Integer.parseInt(cookTimeString);
        } catch (NumberFormatException e) {
            return false;
        }
        if (prepTime < 0 || cookTime < 0) {
            return false;
        }
        return true;
    }
    
    
    private boolean validateEnumFields() {
        try {
            mealTypeEntryComboBox.getSelectionModel().getSelectedItem().toString();
            cuisineEntryComboBox.getSelectionModel().getSelectedItem().toString();
            costEntryComboBox.getSelectionModel().getSelectedItem().toString();
            proteinTypeEntryComboBox.getSelectionModel().getSelectedItem().toString();
            return true;
        } catch (NullPointerException e) {
            return false;
        }
    }
    
    private boolean hasIllegalCharacters(String field) {
        if (field.contains("\\")) {
            hasIllegalCharacterInput = true;
            return true;
        } else if (field.contains("\"")) {
            hasIllegalCharacterInput = true;
            return true;
        } else if (field.contains(":")) {
            hasIllegalCharacterInput = true;
            return true;
        } else if (field.contains("<")) {
            hasIllegalCharacterInput = true;
            return true;
        } else if (field.contains(">")) {
            hasIllegalCharacterInput = true;
            return true;
        } else if (field.contains("*")) {
            hasIllegalCharacterInput = true;
            return true;
        } else if (field.contains("?")) {
            hasIllegalCharacterInput = true;
            return true;
        } else if (field.contains("|")) {
            hasIllegalCharacterInput = true;
            return true;
        }
        return false;
    }
    
    private void initializeFormCriteria() {
        mealTypeEntryComboBox.getItems().removeAll(mealTypeEntryComboBox.getItems());
        mealTypeEntryComboBox.getItems().addAll("Breakfast", "Lunch", "Supper", "Sauce", "Snack", "Dessert");
        
        
        cuisineEntryComboBox.getItems().removeAll(cuisineEntryComboBox.getItems());
        cuisineEntryComboBox.getItems().addAll("American", "Chinese", "Greek", "Indian", "Italian", "Japanese", "Lebanese", "Mexican", "Thai");
        
        
        costEntryComboBox.getItems().removeAll(costEntryComboBox.getItems());
        costEntryComboBox.getItems().addAll("Less than $10", "Between $10 and $15", "Between $15 and $20", "Between $20 and $30", "More than $30");
        
        
        proteinTypeEntryComboBox.getItems().removeAll(proteinTypeEntryComboBox.getItems());
        proteinTypeEntryComboBox.getItems().addAll("Chicken", "Beef", "Pork", "Fish", "Tofu", "Vegetarian", "Dessert");
    }
    
    @FXML
    public void initialize() {
        initializeFormCriteria();
    }

}