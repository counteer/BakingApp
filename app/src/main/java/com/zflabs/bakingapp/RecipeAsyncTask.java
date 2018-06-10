package com.zflabs.bakingapp;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.zflabs.bakingapp.data.Recipe;
import com.zflabs.bakingapp.utils.JsonUtils;
import com.zflabs.bakingapp.utils.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;


public class RecipeAsyncTask extends AsyncTaskLoader<Recipe[]> {

    Recipe[] recipes = null;

    public RecipeAsyncTask(Context context) {
        super(context);
    }

    @Override
    public Recipe[] loadInBackground() {

        URL url = NetworkUtils.buildUrl();
        Recipe[] result = null;
        try {
            String response = NetworkUtils.getResponseFromHttpUrl(url);
            result = JsonUtils.getRecipesFromJsonString(response);
        } catch (IOException | JSONException e) {
            Log.e("MainActivity", e.getMessage(), e);
        }
        return result;
    }
    @Override
    protected void onStartLoading() {
        if (recipes != null) {
            deliverResult(recipes);
        } else {
            forceLoad();
        }
    }

    public void deliverResult(Recipe[] data) {
        recipes = data;
        super.deliverResult(data);
    }
};


