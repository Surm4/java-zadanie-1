package com.surmadziecioljarosz;

import java.util.ArrayList;

public class Pizza implements IName {
    private String name;
    private int menuNo;
    private ArrayList<Ingredient> ingredients;
    private Double ingredientsPrice;

    public Pizza(String name, int menuNo) {
        this.name = name;
        this.ingredientsPrice = 0.0;
        this.menuNo = menuNo;
        this.ingredients = new ArrayList<>();
    }


    /**
     * @param weight - weight of ingredient measured in grams
     */
    public void addIngredient(Ingredient ingredient, int weight) {
        int GRAMS = 100;
        ingredientsPrice += weight * ingredient.getPrice() / GRAMS;
        ingredients.add(ingredient);
    }

    public String getIngredientsList() {
        String ingredientsList = "";

        for (Ingredient i : ingredients) {
            if (ingredientsList == "") {
                ingredientsList = ingredientsList + i.getName();
            } else {
                ingredientsList = ingredientsList + ", " + i.getName();
            }
        }

        return ingredientsList;
    }

    public Double getPrice() {
        Double margin = 1.3;
        return Math.ceil(ingredientsPrice * margin);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public int getMenuNo() {
        return menuNo;
    }

    public void setMenuNo(int menuNo) {
        this.menuNo = menuNo;
    }
}
