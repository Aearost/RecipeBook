package com.aearost.recipebook;

import com.aearost.recipebook.objects.Recipe;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
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
    
//    private static void ensureDirectoryBeforeCreation(String dirPath) {
//        if (dirPath != null && !dirPath.isEmpty()) {
//            File dir = new File(dirPath);
//            if (!dir.exists()) {
//                Path newDirPath = Paths.get(dirPath);
//                if (!Files.exists(newDirPath)) {
//                    try {
//                        Files.createDirectories(newDirPath);
//                    } catch (IOException e) {
//                        throw new RuntimeException("Exception while creating directory: " + dirPath, e);
//                    }
//                }
//            }
//        }
//    }
    
}
