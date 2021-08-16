package com.aearost.recipebook;

import com.aearost.recipebook.objects.Recipe;
import java.io.File;
import java.io.IOException;
import javafx.scene.control.Alert;

public class RecipePersistence {
    
    public static void writeRecipeToFile(Recipe recipe) {
//        String currentPath = System.getProperty("user.dir");
        String userHomePath = System.getProperty("user.home");
        
//        Alert invalidFieldsAlert = new Alert(Alert.AlertType.INFORMATION);
//        invalidFieldsAlert.setHeaderText("Hey!");
//        invalidFieldsAlert.setContentText(currentPath + " | " + userHomePath);
//        invalidFieldsAlert.show();
        

//        System.out.println(currentPath);
        String[] homePathParts = userHomePath.split(File.separator);
        
        String path;
        
        // If it's Windows
        if (File.separator.equals("\\")) {
            path = homePathParts[0] + File.separator + "Program Files";
        }
        // If it's Mac, Linux, etc.
        else {
            path = homePathParts[0] + File.separator + homePathParts[1] + File.separator + "Library" + File.separator + "Application Support";
        }
        
        // Path at the application's data root directory
        // i.e C:\Program Files\RecipeBook
        path += File.separator + "RecipeBook";
        File rootDirectory = new File(path);
        boolean isRootDirectoryCreated = true;
        if (!rootDirectory.isDirectory()) {
            isRootDirectoryCreated = rootDirectory.mkdir();
        }
        if (isRootDirectoryCreated) {
            // Path at the application's data recipe directory
            // i.e C:\Program Files\RecipeBook\recipes
            path += File.separator + "recipes";
            File recipeDirectory = new File(path);
            boolean isRecipeDirectoryCreated = true;
            if (!recipeDirectory.isDirectory()) {
                isRecipeDirectoryCreated = recipeDirectory.mkdir();
            }
            if (isRecipeDirectoryCreated) {
                try {
                    String recipeFileName = recipe.getName().toLowerCase().replaceAll(" ", "_");
                    File recipeFile = new File(path + File.separator + recipeFileName);
                    // If the file isn't already there and the recipe can be added
                    if (recipeFile.createNewFile()) {
                        
                        // Image URL can now be stored
                        
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
                }
            }
        }

//        String filePath = currentPath + File.separator + "plugins" + File.separator + "FlibiCore"
//                        + File.separator + "homes.json";
//        File pluginDirectory = new File(currentPath + File.separator + "plugins" + File.separator + "FlibiCore");
//        File file = new File(filePath);
//
//        // If the directory exists
//        boolean isDirectoryCreated = true;
//        if (!pluginDirectory.isDirectory()) {
//                isDirectoryCreated = pluginDirectory.mkdir();
//        }
//        if (isDirectoryCreated) {
//                try {
//                        // If the file isn't already there
//                        if (file.createNewFile()) {
//                                Bukkit.getLogger().info("A new homes.json file has been generated");
//                        }
//                } catch (IOException e) {
//                        Bukkit.getLogger().info("An error occured in the creation of homes.json");
//                        e.printStackTrace();
//                }
//
//                try {
//                        FileWriter writer = new FileWriter(filePath);
//                        writer.write("{\n");
//                        writer.write("    \"homes\": {\n");
//
//                        int homeCounter = 0;
//                        for (Home home : homes) {
//                                writer.write("        \"homeName\": \"" + home.getHomeName() + "\",\n");
//                                writer.write("        \"worldName\": \"" + home.getLocation().getWorld().getName() + "\",\n");
//                                writer.write("        \"x\": \"" + home.getLocation().getBlockX() + "\",\n");
//                                writer.write("        \"y\": \"" + home.getLocation().getBlockY() + "\",\n");
//                                writer.write("        \"z\": \"" + home.getLocation().getBlockZ() + "\"\n");
//
//                                if (homeCounter + 1 == homes.size()) {
//                                        writer.write("    }\n");
//                                } else {
//                                        writer.write("    },\n");
//                                        writer.write("    {\n");
//                                        homeCounter++;
//                                }
//                        }
//
//                        writer.write("}\n");
//                        writer.close();
//                } catch (IOException e) {
//                        Bukkit.getLogger().info("There was an error in saving the homes");
//                        e.printStackTrace();
//                }
//        }
    }
    
}
