package com.zflabs.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zflabs.bakingapp.data.Ingredients;
import com.zflabs.bakingapp.data.Recipe;
import com.zflabs.bakingapp.data.Steps;
import com.zflabs.bakingapp.utils.JsonUtils;

import java.util.Arrays;


public class StepsFragment extends Fragment implements StepAdapter.StepAdapterClickHandler{

    private OnStepClickListener clickListener;
    private Recipe recipe;

    @Override
    public void onClick(int adapterPosition) {
        Steps[] steps = this.recipe.getSteps();
        if(RecipeHowtoActivity.twoPane){
            Bundle bundle2 = new Bundle();
            bundle2.putString("step", steps[adapterPosition].toJSON().toString());
            StepDetailFragment stepDetailFragment = new StepDetailFragment();

            stepDetailFragment.setArguments(bundle2);
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.step_detail_fragment, stepDetailFragment).commit();
        } else {
            Bundle bundle = new Bundle();
            Steps[] stepsToSend = Arrays.copyOfRange(steps, adapterPosition, steps.length);
            bundle.putString(Intent.EXTRA_TEXT, JsonUtils.getJsonFromSteps(stepsToSend).toString());
            Intent intent = new Intent(getContext(), StepDetailActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    public interface OnStepClickListener {
        void onStepSelected(int position);
    }

    public StepsFragment(){

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
      //  this.clickListener = (OnStepClickListener) context;
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        String steps = args.getString("recipe");
        Recipe recipe = JsonUtils.getRecipeFromJsonString(steps);
        this.recipe = recipe;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.steps_view, container, false);
        TextView ingredients  = (TextView) rootView.findViewById(R.id.tv_ingredients);
        RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.rv_steps);
        ingredients.setText(this.recipe.getIngredintsString());
        StepAdapter adapter = new StepAdapter(this);
        adapter.setSteps(this.recipe.getSteps());

        rv.setLayoutManager(new GridLayoutManager(rootView.getContext(), 1));
        rv.setAdapter(adapter);
        return rootView;
    }

}
