package com.zflabs.bakingapp.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Recipe {
    private String image;

    public Ingredients[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(Ingredients[] ingredients) {
        this.ingredients = ingredients;
    }

    private String name;

    private int id;

    private int servings;

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    private Steps[] steps;

    private Ingredients[] ingredients;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public Steps[] getSteps() {
        return steps;
    }

    public void setSteps(Steps[] steps) {
        this.steps = steps;
    }

    public String toJSON() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);
            jsonObject.put("name", name);
            jsonObject.put("servings", servings);
            JSONArray stepsJson = new JSONArray();
            for (int i = 0; i < steps.length; ++i) {
                JSONObject actualStep = steps[i].toJSON();
                stepsJson.put(i, actualStep);
            }
            jsonObject.put("steps", stepsJson);
            JSONArray ingredientsJson = new JSONArray();
            for (int i = 0; i < ingredients.length; ++i) {
                JSONObject actualIngredient = ingredients[i].toJSON();
                ingredientsJson.put(i, actualIngredient);
            }
            jsonObject.put("ingredients", ingredientsJson);
            jsonObject.put("image", image);
            return jsonObject.toString();
        } catch (JSONException e) {
            return "";
        }
    }

    public String getIngredintsString() {
        String rsult = "";
        for (Ingredients ing : ingredients) {
            rsult += ing.toString() + "\n";
        }
        return rsult;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }
}
