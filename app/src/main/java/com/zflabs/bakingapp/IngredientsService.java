package com.zflabs.bakingapp;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.zflabs.bakingapp.data.Ingredients;

public class IngredientsService extends IntentService {

    public static final String DO_SOMETHING =
            "com.zflabs.bakingapp.do_something";

    public IngredientsService() {
        super("IngredientsService");
    }

    public IngredientsService(String name) {
        super(name);
    }

    public static void startActionDoSomething(Context context, Integer recipeId) {
        Intent intent = new Intent(context, IngredientsService.class);
        intent.setAction(DO_SOMETHING);
        Bundle extras = new Bundle();
        extras.putInt("recipe", recipeId);
        Log.i("IngredientsSErvice", recipeId+"");
        intent.putExtras(extras);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (DO_SOMETHING.equals(action)) {
                Bundle extras = intent.getExtras();
                int recipeId = extras.getInt("recipe");
                handleDoSomething(recipeId);
            }
        }
    }

    private void handleDoSomething(int recipeId) {
        AppWidgetManager manager = AppWidgetManager.getInstance(this);

        int[] appWidgetIds = manager.getAppWidgetIds(new ComponentName(this, IngredientsWidget.class));
        IngredientsWidget.updateAppWidgets(this, manager, recipeId, appWidgetIds);
    }


}
