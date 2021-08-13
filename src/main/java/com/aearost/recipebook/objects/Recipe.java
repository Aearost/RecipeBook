package com.aearost.recipebook.objects;

import java.util.List;

public class Recipe {

    private String name;
    private String description;
    private List<String> ingredients;
    private List<String> steps;
    private MealType mealType;
    private Cuisine cuisine;
    private Cost cost;
    private int prepTime;
    private int cookTime;
    private ProteinType proteinType;
    private String imageUrl;

    public Recipe(String name, String description, List<String> ingredients, List<String> steps, MealType mealType, Cuisine cuisine, Cost cost, int prepTime, int cookTime, ProteinType proteinType) {
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
        this.steps = steps;
        this.mealType = mealType;
        this.cuisine = cuisine;
        this.cost = cost;
        this.prepTime = prepTime;
        this.cookTime = cookTime;
        this.proteinType = proteinType;
    }
    
    public Recipe(String name, String description, List<String> ingredients, List<String> steps, MealType mealType, Cuisine cuisine, Cost cost, int prepTime, int cookTime, ProteinType proteinType, String imageUrl) {
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
        this.steps = steps;
        this.mealType = mealType;
        this.cuisine = cuisine;
        this.cost = cost;
        this.prepTime = prepTime;
        this.cookTime = cookTime;
        this.proteinType = proteinType;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getSteps() {
        return steps;
    }

    public void setSteps(List<String> steps) {
        this.steps = steps;
    }

    public MealType getMealType() {
        return mealType;
    }

    public void setMealType(MealType mealType) {
        this.mealType = mealType;
    }

    public Cuisine getCuisine() {
        return cuisine;
    }

    public void setCuisine(Cuisine cuisine) {
        this.cuisine = cuisine;
    }
    
    public Cost getCost() {
        return cost;
    }
    
    public void setCost(Cost cost) {
        this.cost = cost;
    }

    public int getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(int prepTime) {
        this.prepTime = prepTime;
    }

    public int getCookTime() {
        return cookTime;
    }

    public void setCookTime(int cookTime) {
        this.cookTime = cookTime;
    }

    public ProteinType getProteinType() {
        return proteinType;
    }

    public void setProteinType(ProteinType proteinType) {
        this.proteinType = proteinType;
    }
    
    @Override
    public String toString() {
        String lineSeparator = "------------------------\n";
        String nameLine = "Recipe Name: " + this.name + "\n";
        String descriptionLine = "Description: " + this.description + "\n";
        String ingredientsLine = "Ingredients: ";
        for (int i = 0; i < this.ingredients.size(); i++) {
            if (i == this.ingredients.size() - 1) {
                ingredientsLine += this.ingredients.get(i);
            } else {
                ingredientsLine += this.ingredients.get(i) + "***";
            }
        }
        ingredientsLine += "\n";
        String stepsLine = "Steps: ";
        for (int i = 0; i < this.steps.size(); i++) {
            if (i == this.steps.size() - 1) {
                stepsLine += this.steps.get(i);
            } else {
                stepsLine += this.steps.get(i) + "***";
            }
        }
        stepsLine += "\n";
        String mealTypeLine = "Meal Type: " + this.mealType.toString() + "\n";
        String cuisineLine = "Cuisine: " + this.cuisine.toString() + "\n";
        String costLine = "Cost: " + this.cost.toString() + "\n";
        String prepTimeLine = "Prep Time: " + this.prepTime + "\n";
        String cookTimeLine = "Cook Time: " + this.cookTime + "\n";
        String proteinTypeLine = "Protein Type: " + this.proteinType.toString();
        if (this.imageUrl != null) {
            proteinTypeLine += "\n";
            String imageUrlLine = "Image URL: " + this.imageUrl;
            return lineSeparator + nameLine + descriptionLine + ingredientsLine + stepsLine + mealTypeLine + cuisineLine + costLine + prepTimeLine + cookTimeLine + proteinTypeLine + imageUrlLine;
        }
        return lineSeparator + nameLine + descriptionLine + ingredientsLine + stepsLine + mealTypeLine + cuisineLine + costLine + prepTimeLine + cookTimeLine + proteinTypeLine;
    }

}
