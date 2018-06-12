package com.zflabs.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.zflabs.bakingapp.data.Steps;
import com.zflabs.bakingapp.utils.JsonUtils;

public class StepDetailActivity extends AppCompatActivity {

    private Steps[] steps;

    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);
        Intent intentThatStartedThisActivity = getIntent();
        Log.i("StepDetailActivity", "onCreate");
        if (savedInstanceState == null) {

            if (intentThatStartedThisActivity != null && intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {
                String stepString = intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT);
                StepDetailFragment stepDetailFragment = new StepDetailFragment();
                this.steps = JsonUtils.getStepsFromString(stepString);
                Bundle bundle = new Bundle();
                bundle.putString("step", this.steps[0].toJSON().toString());
                bundle.putBoolean("last", this.steps.length == 1);
                stepDetailFragment.setArguments(bundle);
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().add(R.id.step_detail_fragment, stepDetailFragment).commit();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public void nextStep(View button) {
        position++;
        StepDetailFragment stepDetailFragment = new StepDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("step", this.steps[position].toJSON().toString());
        bundle.putBoolean("last", this.steps.length - position == 1);
        stepDetailFragment.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.step_detail_fragment, stepDetailFragment).commit();
    }

}
