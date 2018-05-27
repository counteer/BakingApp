package com.zflabs.bakingapp.utils;

import android.support.annotation.NonNull;
import android.util.Log;

import com.zflabs.bakingapp.data.Recipe;
import com.zflabs.bakingapp.data.Steps;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtils {

    public static Recipe[] getRecipesFromJsonString(String recipesString) throws JSONException{

        JSONArray recipeArray = new JSONArray(recipesString);
        Recipe[] recipes = new Recipe[recipeArray.length()];
        for (int i = 0; i < recipeArray.length(); i++) {
            JSONObject actualRecipeJson = recipeArray.getJSONObject(i);
            Recipe actualRecipe = getRecipeFromString(actualRecipeJson);
            recipes[i] = actualRecipe;
        }
        return recipes;
    }

    public static Recipe getRecipeFromJsonString(String recipe)  {
        try {
            JSONObject result = new JSONObject(recipe);
            return getRecipeFromString(result);
        } catch (JSONException jsone){
            return new Recipe();
        }
    }
    @NonNull
    private static Recipe getRecipeFromString(JSONObject actualRecipeJson) throws JSONException {
        Recipe actualRecipe = new Recipe();
        actualRecipe.setId(actualRecipeJson.getInt("id"));
        actualRecipe.setName(actualRecipeJson.getString("name"));
        Steps[] steps = getSteps(actualRecipeJson);
        actualRecipe.setSteps(steps);
        return actualRecipe;
    }

    public static Steps getStep(String stepsString){
        Steps steps = new Steps();
        try {
            JSONObject stepJson = new JSONObject(stepsString);
            steps= getStep(stepJson);
        } catch (JSONException e) {
            Log.e("JsonUtils", e.getMessage());
        }
        return steps;
    }

    private static Steps[] getSteps(JSONObject actualRecipeJson) throws JSONException {
        JSONArray steps = actualRecipeJson.getJSONArray("steps");
        Steps[] result = new Steps[steps.length()];
        for(int i = 0; i < steps.length();++i){
            JSONObject jsonObject = steps.getJSONObject(i);
            Steps actualSteps = getStep(jsonObject);
            result[i] = actualSteps;
        }
        return result;
    }

    @NonNull
    private static Steps getStep(JSONObject jsonObject) {
        Steps actualSteps = new Steps();
        actualSteps.setId(jsonObject.optInt("id", 0));
        actualSteps.setDescription(jsonObject.optString("description", ""));
        actualSteps.setShortDescription(jsonObject.optString("shortDescription", ""));
        actualSteps.setThumbNailURL(jsonObject.optString("thumbnailURL", ""));
        actualSteps.setVideoURL(jsonObject.optString("videoURL", ""));
        return actualSteps;
    }
}
