package com.zflabs.bakingapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.zflabs.bakingapp.data.Recipe;
import com.zflabs.bakingapp.utils.JsonUtils;
import com.zflabs.bakingapp.utils.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Recipe[]>, RecipeAdapter.RecipeAdapterClickHandler{

    private static final int RECIPE_LOADER_ID = 213;

    @BindView(R.id.rvRecipes)
    RecyclerView recipes;

    private RecipeAdapter recipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        int loaderId = RECIPE_LOADER_ID;
        recipes.setLayoutManager(new GridLayoutManager(this, 1));
        recipeAdapter = new RecipeAdapter(MainActivity.this);
        recipes.setAdapter(recipeAdapter);
        LoaderManager.LoaderCallbacks<Recipe[]> callback = MainActivity.this;
        Bundle bundleForLoader = null;
        getSupportLoaderManager().initLoader(loaderId, bundleForLoader, callback);

    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public Loader<Recipe[]> onCreateLoader(int i, Bundle bundle) {
        return new AsyncTaskLoader<Recipe[]>(this) {

            Recipe[] recipes = null;

            @Override
            public Recipe[] loadInBackground() {
                Log.i("almafe", "ott");

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
    }

    @Override
    public void onLoadFinished(Loader<Recipe[]> loader, Recipe[] recipe) {
        recipeAdapter.setRecipes(recipe);
        recipes.setAdapter(recipeAdapter);
    }

    @Override
    public void onLoaderReset(Loader<Recipe[]> loader) {

    }

    @Override
    public void onClick(Recipe recipe) {
        Context context = this;
        Class destinationClass = RecipeHowtoActivity.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);
        intentToStartDetailActivity.putExtra(Intent.EXTRA_TEXT, recipe.toJSON());
        startActivity(intentToStartDetailActivity);
    }
}
