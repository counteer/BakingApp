package com.zflabs.bakingapp;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class StepDetailActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);
        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null && intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {
            String stepString = intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT);
            StepDetailFragment stepDetailFragment = new StepDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putString("step", stepString);
            stepDetailFragment.setArguments(bundle);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().add(R.id.step_detail_fragment, stepDetailFragment).commit();
        }
    }
}
