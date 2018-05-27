package com.zflabs.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.zflabs.bakingapp.data.Recipe;
import com.zflabs.bakingapp.data.Steps;
import com.zflabs.bakingapp.utils.JsonUtils;

import org.json.JSONObject;


public class StepsFragment extends Fragment implements StepAdapter.StepAdapterClickHandler{

    Steps[] steps;

    private OnStepClickListener clickListener;

    @Override
    public void onClick(Steps steps) {
        Bundle bundle =  new Bundle();
        JSONObject stepJson = steps.toJSON();
        bundle.putString("step", stepJson.toString());
        Intent intent = new Intent(getContext(), StepDetailActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT, stepJson.toString());
        startActivity(intent);
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
        this.steps = recipe.getSteps();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.steps_view, container, false);
        TextView ingredients  = (TextView) rootView.findViewById(R.id.tv_ingredients);
        RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.rv_steps);
        StepAdapter adapter = new StepAdapter(this);
        adapter.setSteps(steps);

        rv.setLayoutManager(new GridLayoutManager(rootView.getContext(), 1));
        rv.setAdapter(adapter);
//        View.OnClickListener listener = new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                clickListener.onStepSelected(0);
//            }
//        };
//        rv.setOnClickListener(listener);
        return rootView;
    }

}
