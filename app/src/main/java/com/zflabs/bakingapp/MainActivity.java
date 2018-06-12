package com.zflabs.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zflabs.bakingapp.data.Recipe;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements
        RecipeAdapter.RecipeAdapterClickHandler {

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
        LoaderManager.LoaderCallbacks<Recipe[]> callback = new RecipeLoaderCallback(this, recipeAdapter, recipes);
        Bundle bundleForLoader = null;
        getSupportLoaderManager().initLoader(loaderId, bundleForLoader, callback);

    }

    @Override
    public void onClick(Recipe recipe) {
        Context context = this;
        Class destinationClass = RecipeHowtoActivity.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);
        intentToStartDetailActivity.putExtra(Intent.EXTRA_TEXT, recipe.toJSON());
        IngredientsService.startActionDoSomething(this, recipe.getId());
        startActivity(intentToStartDetailActivity);
    }
}
