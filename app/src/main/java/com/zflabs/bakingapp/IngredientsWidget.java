package com.zflabs.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.widget.RemoteViews;

import com.google.android.exoplayer2.offline.Downloader;
import com.zflabs.bakingapp.data.Recipe;
import com.zflabs.bakingapp.utils.JsonUtils;
import com.zflabs.bakingapp.utils.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientsWidget extends AppWidgetProvider {

    private static Integer loaderId = 2213;

    static void updateAppWidgets(Context context, AppWidgetManager appWidgetManager,
                                int recipeId, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, recipeId, appWidgetId);
        }
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int recipeId, int appWidgetId) {
        final RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredients_widget);
        Intent intent  = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0, intent, 0);
        views.setOnClickPendingIntent(R.id.widget_ingredient_list, pendingIntent);
        Log.i("IngredientsService", recipeId+"");
        new DownloadFilesTask(context, appWidgetId, appWidgetManager).execute(recipeId);
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        updateAppWidget(context, appWidgetManager, 0, appWidgetId);

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    static class DownloadFilesTask extends AsyncTask<Integer, Void,  Recipe> {
        private final Context context;
        private final AppWidgetManager appWidgetManager;
        private final int appWidgetId;

        DownloadFilesTask(Context context, int appWidgetId, AppWidgetManager appWidgetManager){
            this.context = context;
            this.appWidgetId = appWidgetId;
            this.appWidgetManager = appWidgetManager;

        }
        protected Recipe doInBackground(Integer... recipeId) {
            URL url = NetworkUtils.buildUrl();
            Recipe[] result = null;
            try {
                String response = NetworkUtils.getResponseFromHttpUrl(url);
                result = JsonUtils.getRecipesFromJsonString(response);
            } catch (IOException | JSONException e) {
                Log.e("Widget", e.getMessage(), e);
            }
            Integer neededRecipe = recipeId[0]==0?0:recipeId[0]-1;
            return result != null ? result[neededRecipe] : null;
        }

        protected void onPostExecute(Recipe recipe) {
            final RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredients_widget);
            views.setTextViewText(R.id.widget_ingredient_list, recipe.getIngredintsString());
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }
}

