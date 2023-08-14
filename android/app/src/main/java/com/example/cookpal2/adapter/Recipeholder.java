package com.example.cookpal2.adapter;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cookpal2.R;

public class Recipeholder extends RecyclerView.ViewHolder {

    TextView recipe_name, Category, url, prep_time, cuisine_path, cook_time, total_time, servings, yield, ingredients, directions, rating, nutrition, timing, img_src;

    public Recipeholder(@NonNull View itemView) {
        super(itemView);
        recipe_name = itemView.findViewById(R.id.recipe_name);
        Category = itemView.findViewById(R.id.category);
        prep_time = itemView.findViewById(R.id.prep_time);
        cook_time = itemView.findViewById(R.id.cook_time);
        servings = itemView.findViewById(R.id.servings);
        ingredients = itemView.findViewById(R.id.ingredients);
        directions = itemView.findViewById(R.id.directions);
        rating = itemView.findViewById(R.id.ratings);
        img_src = itemView.findViewById(R.id.imageButton);
    }
}