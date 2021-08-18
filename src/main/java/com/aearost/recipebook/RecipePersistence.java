package com.aearost.recipebook;

import com.aearost.recipebook.objects.Cost;
import com.aearost.recipebook.objects.Cuisine;
import com.aearost.recipebook.objects.MealType;
import com.aearost.recipebook.objects.ProteinType;
import com.aearost.recipebook.objects.Recipe;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javafx.scene.control.Alert;

public class RecipePersistence {
    
    public static void writeRecipeToFile(Recipe recipe) {
        String path = System.getProperty("user.home") + File.separator + "Documents" + File.separator + "RecipeBook";
        File rootDirectory = new File(path);
        boolean isRootDirectoryCreated = true;
        
        if (!rootDirectory.isDirectory()) {
            isRootDirectoryCreated = rootDirectory.mkdirs();
        }
        if (isRootDirectoryCreated) {
            // Path at the application's data recipe directory
            // i.e C:\Program Files\RecipeBook\recipes
            String imagesPath = path + File.separator + "images";
            path += File.separator + "recipes";
            File recipeDirectory = new File(path);
            boolean isRecipeDirectoryCreated = true;
            
            if (!recipeDirectory.isDirectory()) {
                isRecipeDirectoryCreated = recipeDirectory.mkdir();
            }
            if (isRecipeDirectoryCreated) {
                try {
                    String recipeFileName = recipe.getName().toLowerCase().replaceAll(" ", "_");
                    path += File.separator + recipeFileName + ".json";
                    File recipeFile = new File(path);
                    // If the file isn't already there and the recipe can be added
                    if (recipeFile.createNewFile()) {
                        
                        try (FileWriter writer = new FileWriter(path)) {
                            writer.write("{\n");
                            
                            writer.write("    \"name\": \"" + recipe.getName() + "\",\n");
                            writer.write("    \"description\": \"" + recipe.getDescription() + "\",\n");
                            writer.write("    \"ingredients\": [ ");
                            for (int i = 0; i < recipe.getIngredients().size(); i++) {
                                // If it's the last ingredient, do not use a comma at the end
                                if (i == recipe.getIngredients().size() - 1) {
                                    writer.write("\"" + recipe.getIngredients().get(i) + "\" ],\n");
                                } else {
                                    writer.write("\"" + recipe.getIngredients().get(i) + "\", ");
                                }
                            }
                            writer.write("    \"steps\": [ ");
                            for (int i = 0; i < recipe.getSteps().size(); i++) {
                                // If it's the last step, do not use a comma at the end
                                if (i == recipe.getSteps().size() - 1) {
                                    writer.write("\"" + recipe.getSteps().get(i) + "\" ],\n");
                                } else {
                                    writer.write("\"" + recipe.getSteps().get(i) + "\", ");
                                }
                            }
                            writer.write("    \"mealType\": \"" + recipe.getMealType().toString() + "\",\n");
                            writer.write("    \"cuisine\": \"" + recipe.getCuisine().toString() + "\",\n");
                            writer.write("    \"cost\": \"" + recipe.getCost().toString() + "\",\n");
                            writer.write("    \"prepTime\": \"" + recipe.getPrepTime() + "\",\n");
                            writer.write("    \"cookTime\": \"" + recipe.getCookTime() + "\",\n");
                            writer.write("    \"proteinType\": \"" + recipe.getProteinType().toString() + "\",\n");
                            
                            
                            // If there was an image
                            if (!recipe.getImageUrl().equals("")) {
                                File imagesDirectory = new File(imagesPath);
                                boolean isImagesDirectoryCreated = true;

                                if (!imagesDirectory.isDirectory()) {
                                    isImagesDirectoryCreated = imagesDirectory.mkdir();
                                }
                                
                                String[] pathParts = recipe.getImageUrl().split("\\.");
                                String extension = pathParts[pathParts.length - 1];
                                String localImageUrl = recipe.getImageUrl();
                                
                                recipe.setImageUrl(imagesDirectory + File.separator + recipeFileName + "." + extension);
                                // Copies the image to the \RecipeBook\images folder
                                Files.copy(new File(localImageUrl).toPath(), new File(recipe.getImageUrl()).toPath(), StandardCopyOption.REPLACE_EXISTING);
                            }
                            writer.write("    \"imageUrl\": \"" + recipe.getImageUrl() + "\"\n");
                            
                            writer.write("}");   
                        }
                        
                        Alert recipeAddSuccessAlert = new Alert(Alert.AlertType.INFORMATION);
                        recipeAddSuccessAlert.setHeaderText(recipe.getName() + " was added to the Recipe Book successfully!");
                        recipeAddSuccessAlert.setContentText("Moving forward, " + recipe.getName() + " will now appear in your searches.");
                        recipeAddSuccessAlert.show();
                    }
                    // There is already a recipe with this name
                    else {
                        Alert recipeAddExistsAlert = new Alert(Alert.AlertType.ERROR);
                        recipeAddExistsAlert.setHeaderText("The recipe could not be added.");
                        recipeAddExistsAlert.setContentText("There is already a recipe with this same name. Please try to use another.");
                        recipeAddExistsAlert.show();
                    }
                } catch (IOException e) {
                    Alert recipeAddFailureAlert = new Alert(Alert.AlertType.ERROR);
                    recipeAddFailureAlert.setHeaderText("The recipe could not be added.");
                    recipeAddFailureAlert.setContentText("Something went wrong. Please contact Liam regarding what happened.");
                    recipeAddFailureAlert.show();
                    e.printStackTrace();
                }
            }
        }
    }
    

    public static void readRecipesFromFiles() {
        String path = System.getProperty("user.home") + File.separator + "Documents" + File.separator + "RecipeBook" + File.separator + "recipes";
        File recipeDirectory = new File(path);
        
        if (recipeDirectory.isDirectory()) {
            Scanner reader;
            for (final File recipeFile : recipeDirectory.listFiles()) {
                try {
                    reader = new Scanner(recipeFile);
                    int fieldCount = 0;
                    String fieldName;
                    String fieldValue;
                    
                    String name = null;
                    String description = null;
                    List<String> ingredients = null;
                    List<String> steps = null;
                    MealType mealType = null;
                    Cuisine cuisine = null;
                    Cost cost = null;
                    int prepTime = 0;
                    int cookTime = 0;
                    ProteinType proteinType = null;
                    String imageUrl = null;

                    while (reader.hasNextLine()) {
                        String line = reader.nextLine();
                        String[] parts = line.split("\"");

                        if (line.equals("{") || line.equals("}")) {
                            continue;
                        }
                        
                        if (parts[parts.length - 1].contains(",") || parts[1].equals("imageUrl")) {
                            fieldName = parts[1];
                            if (parts.length > 3) {
                                fieldValue = parts[3];
                            } else {
                                fieldValue = "";
                            }
                        } else {
                            continue;
                        }

                        if (fieldName.equals("name")) {
                            name = fieldValue;
                            fieldCount++;
                        }
                        else if (fieldName.equals("description")) {
                            description = fieldValue;
                            fieldCount++;
                        } else if (fieldName.equals("ingredients")) {
                            ingredients = new ArrayList<>();
                            int finalPart = parts.length - 2;
                            // Cycles through the elements in the array
                            for (int i = 3; i < finalPart; i = i + 2) {
                                ingredients.add(parts[i]);
                            }
                            fieldCount++;
                        } else if (fieldName.equals("steps")) {
                            steps = new ArrayList<>();
                            int finalPart = parts.length - 2;
                            // Cycles through the elements in the array
                            for (int i = 3; i < finalPart; i = i + 2) {
                                steps.add(parts[i]);
                            }
                            fieldCount++;
                        } else if (fieldName.equals("mealType")) {
                            mealType = MealType.valueOf(fieldValue);
                            fieldCount++;
                        } else if (fieldName.equals("cuisine")) {
                            cuisine = Cuisine.valueOf(fieldValue);
                            fieldCount++;
                        } else if (fieldName.equals("cost")) {
                            cost = Cost.valueOf(fieldValue);
                            fieldCount++;
                        } else if (fieldName.equals("prepTime")) {
                            prepTime = Integer.parseInt(fieldValue);
                            fieldCount++;
                        } else if (fieldName.equals("cookTime")) {
                            cookTime = Integer.parseInt(fieldValue);
                            fieldCount++;
                        } else if (fieldName.equals("proteinType")) {
                            proteinType = ProteinType.valueOf(fieldValue);
                            fieldCount++;
                        } else if (fieldName.equals("imageUrl")) {
                            imageUrl = fieldValue;
                            fieldCount++;
                        }

                        if (fieldCount == 11) {
                            Recipe recipe = new Recipe(name, description, ingredients, steps, mealType, cuisine, cost, prepTime, cookTime, proteinType, imageUrl);
                            RecipeUtils.addRecipe(recipe);
                            fieldCount = 0;
                            System.out.println(name + " has been loaded");
                        }
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                
            }
                
        }
    }
        
        
}
