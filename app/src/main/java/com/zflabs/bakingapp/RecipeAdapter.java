package com.zflabs.bakingapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zflabs.bakingapp.data.Recipe;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeAdapterViewHolder> {

    private Recipe[] recipes = new Recipe[0];

    private RecipeAdapterClickHandler clickHandler;

    RecipeAdapter(RecipeAdapterClickHandler clickHandler) {
        this.clickHandler = clickHandler;
    }

    public interface RecipeAdapterClickHandler {
        void onClick(Recipe recipe);
    }

    public class RecipeAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_recipe_name)
        TextView recipeName;

        @BindView(R.id.tv_servings)
        TextView servings;

        @BindView(R.id.recipe_image)
        ImageView recipeImage;

        public RecipeAdapterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Recipe recipe = recipes[adapterPosition];
            clickHandler.onClick(recipe);
        }
    }

    @Override
    public RecipeAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.recipe_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new RecipeAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeAdapterViewHolder holder, int position) {
        Context context = holder.recipeImage.getContext();
        Recipe recipe = recipes[position];
        holder.recipeName.setText(recipe.getName());
        String image = recipe.getImage();
        if(image==null||"".equals(image)){
            Picasso.with(context).load(R.drawable.noimage).into(holder.recipeImage);
        } else {
            Picasso.with(context).load(image).into(holder.recipeImage);
        }
    }

    @Override
    public int getItemCount() {
        if (recipes == null) {
            return 0;
        }
        return recipes.length;
    }

    public void setRecipes(Recipe[] recipes) {
        this.recipes = recipes;
    }
}
