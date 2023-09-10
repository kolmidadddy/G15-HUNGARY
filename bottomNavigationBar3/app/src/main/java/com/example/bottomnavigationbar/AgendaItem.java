package com.example.bottomnavigationbar;

public class AgendaItem {

    String date;
    String mealDescription;
    String ingredients;

    public AgendaItem(String date, String mealDescription, String ingredients) {
        this.date = date;
        this.mealDescription = mealDescription;
        this.ingredients = ingredients;
    }

    public AgendaItem() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMealDescription() {
        return mealDescription;
    }

    public void setMealDescription(String mealDescription) {
        this.mealDescription = mealDescription;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }
}
