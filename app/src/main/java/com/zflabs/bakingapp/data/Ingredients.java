package com.zflabs.bakingapp.data;

import org.json.JSONException;
import org.json.JSONObject;

public class Ingredients {

    private String quantity;

    private String measure;

    private String ingredient;

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("ingredient", ingredient);
            jsonObject.put("quantity", quantity);
            jsonObject.put("measure", measure);
            return jsonObject;
        } catch (JSONException e) {
            return new JSONObject();
        }
    }

    public String toString() {
        return quantity + " " + measure + " " + ingredient;
    }
}
