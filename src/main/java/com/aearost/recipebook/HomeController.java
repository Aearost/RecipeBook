package com.aearost.recipebook;

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
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;

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
