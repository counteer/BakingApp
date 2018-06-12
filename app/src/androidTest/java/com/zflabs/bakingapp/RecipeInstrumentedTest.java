package com.zflabs.bakingapp;

import android.content.Intent;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class RecipeInstrumentedTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Rule
    public IntentsTestRule<RecipeHowtoActivity> mActivityRule = new IntentsTestRule<>(
            RecipeHowtoActivity.class);

    @Test
    public void checkNutellaClickable() throws Exception {
        onView(withText("Nutella Pie")).perform(click());
        intended(allOf(
                hasExtraWithKey(Intent.EXTRA_TEXT),
                hasComponent(RecipeHowtoActivity.class.getName()),
                toPackage("com.zflabs.bakingapp")
        ));
    }
}
