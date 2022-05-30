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

public class RecipeEditController {

    @FXML
    private TextField nameEditTextField;
    @FXML
    private TextArea descriptionEditTextArea;
    @FXML
    private TextArea ingredientsEditTextArea;
    @FXML
    private TextArea stepsEditTextArea;
    @FXML
    private ComboBox recipeListComboBox;
    @FXML
    private ComboBox mealTypeEditComboBox;
    @FXML
    private ComboBox cuisineEditComboBox;
    @FXML
    private ComboBox costEditComboBox;
    @FXML
    private TextField prepTimeEditTextField;
    @FXML
    private TextField cookTimeEditTextField;
    @FXML
    private ComboBox proteinTypeEditComboBox;
    @FXML
    private Button imageAttachButton;
    @FXML
    private Text imagePathText;
    @FXML
    private Button recipeEditBackButton;
    @FXML
    private Button recipeEditRecipeButton;
    
    private boolean hasIllegalCharacterInput = false;
    
    @FXML
    private void onRecipeEditBackButtonClick() throws IOException {
        App.setRoot("home");
    }
    
    @FXML
    private void onRecipeListComboBoxChange() {
        Recipe recipe = RecipeUtils.getRecipeByName(recipeListComboBox.getSelectionModel().getSelectedItem().toString());
        nameEditTextField.setText(recipe.getName());
        descriptionEditTextArea.setText(recipe.getDescription());
        
        ingredientsEditTextArea.setWrapText(true);
        ingredientsEditTextArea.setText(RecipeUtils.getTextAreaStringFromList(recipe.getIngredients()));
        
        stepsEditTextArea.setWrapText(true);
        stepsEditTextArea.setText(RecipeUtils.getTextAreaStringFromList(recipe.getSteps()));
        
        mealTypeEditComboBox.getSelectionModel().select(RecipeUtils.getComboBoxIndexFromEnumString(
                mealTypeEditComboBox, recipe.getMealType().toString()));
        cuisineEditComboBox.getSelectionModel().select(RecipeUtils.getComboBoxIndexFromEnumString(
                cuisineEditComboBox, recipe.getCuisine().toString()));
        proteinTypeEditComboBox.getSelectionModel().select(RecipeUtils.getComboBoxIndexFromEnumString(
                proteinTypeEditComboBox, recipe.getProteinType().toString()));
        
        costEditComboBox.getSelectionModel().select(getComboBoxIndexFromCost(recipe.getCost().toString()));
        
        prepTimeEditTextField.setText(recipe.getPrepTime() + "");
        cookTimeEditTextField.setText(recipe.getCookTime() + "");
        
        imagePathText.setText(recipe.getImageUrl());
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
    private void onRecipeEditSaveRecipeButtonClick() throws IOException {
        System.out.println("Implement stuff here! RecipeEditController line 191");
        
        String name = nameEditTextField.getText();
        String description = descriptionEditTextArea.getText();
        List<String> ingredients = new ArrayList<>();
        ingredients.addAll(Arrays.asList(ingredientsEditTextArea.getText().split("\n")));
        
        List<String> steps = new ArrayList<>();
        steps.addAll(Arrays.asList(stepsEditTextArea.getText().split("\n")));
        
        // Note that imageUrl is optional so it does not need to be validated
        boolean isFieldsValidated = validateFields(name, description, ingredients, steps);
        boolean isNumberFieldsValidated = validateNumberFields(prepTimeEditTextField.getText(), cookTimeEditTextField.getText());
        boolean isEnumFieldsValidated = validateEnumFields();
        
        if (isFieldsValidated && isNumberFieldsValidated && isEnumFieldsValidated) {
            // Initialize the number fields
            int prepTime = Integer.parseInt(prepTimeEditTextField.getText());
            int cookTime = Integer.parseInt(cookTimeEditTextField.getText());
            
            //Initialize the enum fields
            MealType mealType = MealType.valueOf(mealTypeEditComboBox.getSelectionModel().getSelectedItem().toString().toUpperCase());
            Cuisine cuisine = Cuisine.valueOf(cuisineEditComboBox.getSelectionModel().getSelectedItem().toString().toUpperCase());

            Cost cost;
            String costContent = costEditComboBox.getSelectionModel().getSelectedItem().toString();
            switch (costContent) {
                case "Less than $15":
                    cost = Cost.LT15;
                    break;
                case "Less than $20":
                    cost = Cost.LT20;
                    break;
                case "Less than $30":
                    cost = Cost.LT30;
                    break;
                case "More than $30":
                    cost = Cost.GT30;
                    break;
                default:
                    cost = Cost.LT10;
                    break;
            }

            ProteinType proteinType = ProteinType.valueOf(proteinTypeEditComboBox.getSelectionModel().getSelectedItem().toString().toUpperCase());
            
            // No image was selected
            Recipe recipe;
            if (imagePathText.getText().equals("")) {
                recipe = new Recipe(name, description, ingredients, steps, mealType, cuisine, cost, prepTime, cookTime, proteinType);
            }
            // An image was selected
            else {
                recipe = new Recipe(name, description, ingredients, steps, mealType, cuisine, cost, prepTime, cookTime, proteinType, imagePathText.getText());
            }
            RecipePersistence.writeRecipeToFile(recipe);
            RecipeUtils.addRecipe(recipe);
            System.out.println("Updated recipe successfully!");
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
            mealTypeEditComboBox.getSelectionModel().getSelectedItem().toString();
            cuisineEditComboBox.getSelectionModel().getSelectedItem().toString();
            costEditComboBox.getSelectionModel().getSelectedItem().toString();
            proteinTypeEditComboBox.getSelectionModel().getSelectedItem().toString();
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
        recipeListComboBox.getItems().removeAll(recipeListComboBox.getItems());
        for (Recipe recipe : RecipeUtils.getRecipes()) {
            recipeListComboBox.getItems().add(recipe.getName());
        }
        
        mealTypeEditComboBox.getItems().removeAll(mealTypeEditComboBox.getItems());
        mealTypeEditComboBox.getItems().addAll("Breakfast", "Lunch", "Supper", "Sauce", "Snack", "Dessert");
        
        cuisineEditComboBox.getItems().removeAll(cuisineEditComboBox.getItems());
        cuisineEditComboBox.getItems().addAll("American", "Chinese", "Greek", "Indian", "Italian", "Japanese", "Lebanese", "Mexican", "Thai");
        
        costEditComboBox.getItems().removeAll(costEditComboBox.getItems());
        costEditComboBox.getItems().addAll("Under $10", "Less than $15", "Less than $20", "Less than $30", "More than $30");
        
        proteinTypeEditComboBox.getItems().removeAll(proteinTypeEditComboBox.getItems());
        proteinTypeEditComboBox.getItems().addAll("Chicken", "Beef", "Pork", "Fish", "Tofu", "Vegetarian", "Dessert");
    }
    
    private int getComboBoxIndexFromCost(String recipeCost) {
        if (recipeCost.equals(Cost.LT10.toString())) {
            return 0;
        } else if (recipeCost.equals(Cost.LT15.toString())) {
            return 1;
        } else if (recipeCost.equals(Cost.LT20.toString())) {
            return 2;
        } else if (recipeCost.equals(Cost.LT30.toString())) {
            return 3;
        } else {
            return 4;
        }
    }
    
    @FXML
    public void initialize() {
        initializeFormCriteria();
    }
}