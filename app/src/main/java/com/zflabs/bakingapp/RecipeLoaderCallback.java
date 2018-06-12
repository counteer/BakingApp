package com.zflabs.bakingapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.RecyclerView;

import com.zflabs.bakingapp.data.Recipe;

public class RecipeLoaderCallback implements LoaderManager.LoaderCallbacks<Recipe[]> {

    private final Context context;

    RecyclerView recipes;

    private RecipeAdapter recipeAdapter;

    public RecipeLoaderCallback(Context context, RecipeAdapter recipeAdapter, RecyclerView rv) {
        this.context = context;
        this.recipeAdapter = recipeAdapter;
        this.recipes = rv;
    }

    @Override
    public Loader<Recipe[]> onCreateLoader(int i, Bundle bundle) {
        return new RecipeAsyncTask(context);
    }

    @Override
    public void onLoadFinished(Loader<Recipe[]> loader, Recipe[] recipe) {
        recipeAdapter.setRecipes(recipe);
        recipes.setAdapter(recipeAdapter);
    }

    @Override
    public void onLoaderReset(Loader<Recipe[]> loader) {

    }
}
