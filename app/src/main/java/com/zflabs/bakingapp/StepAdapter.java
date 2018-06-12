package com.zflabs.bakingapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zflabs.bakingapp.data.Steps;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepHolder> {

    private Steps[] steps;

    private final StepAdapterClickHandler clickHandler;

    public StepAdapter(StepAdapterClickHandler clickHandler) {
        this.clickHandler = clickHandler;
    }

    public interface StepAdapterClickHandler {
        void onClick(int adapterPosition);
    }

    public class StepHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.step_description)
        TextView textView;

        public StepHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            clickHandler.onClick(adapterPosition);
        }
    }

    @Override
    public StepHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.step_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new StepAdapter.StepHolder(view);
    }

    @Override
    public void onBindViewHolder(StepHolder holder, int position) {
        holder.textView.setText(steps[position].getShortDescription());
    }

    @Override
    public int getItemCount() {
        return steps == null ? 0 : steps.length;
    }

    public void setSteps(Steps[] steps) {
        this.steps = steps;
    }

}
