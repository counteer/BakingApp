package com.zflabs.bakingapp;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.zflabs.bakingapp.StepsFragment.OnStepClickListener;
import com.zflabs.bakingapp.data.Recipe;
import com.zflabs.bakingapp.utils.JsonUtils;

import org.json.JSONObject;

public class RecipeHowtoActivity extends AppCompatActivity implements OnStepClickListener {

    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_howto);
        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity != null && intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {
            String recipeString = intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT);
            Recipe recipe = JsonUtils.getRecipeFromJsonString(recipeString);
            this.recipe = recipe;
            StepsFragment stepsFragment = new StepsFragment();
            Bundle bundle =  new Bundle();
            bundle.putString("recipe", recipe.toJSON());
            stepsFragment.setArguments(bundle);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().add(R.id.steps_fragment, stepsFragment).commit();
        }
    }

    @Override
    public void onStepSelected(int position) {
        Bundle bundle =  new Bundle();
        String step = recipe.getSteps()[position].toJSON().toString();
        bundle.putString(Intent.EXTRA_TEXT, step);
        Intent intent = new Intent(this, StepDetailActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
