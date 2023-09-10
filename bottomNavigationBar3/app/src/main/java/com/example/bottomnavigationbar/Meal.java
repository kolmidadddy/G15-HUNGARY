// done by Samantha Lok
package com.example.bottomnavigationbar;

import java.util.ArrayList;

public class Meal {
    String description;
    String date;
    ArrayList<String> ingredient;

    public Meal() {
    }

    public Meal(String description,String date, ArrayList<String> ingredient) {
        this.description = description;
        this.date=date;
        this.ingredient = ingredient;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<String> getIngredient() {
        return ingredient;
    }

    public void setIngredient(ArrayList<String> ingredients) {
        this.ingredient = ingredients;
    }
}
