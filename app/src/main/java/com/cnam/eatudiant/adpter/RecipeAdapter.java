package com.cnam.eatudiant.adpter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.cnam.eatudiant.R;
import com.cnam.eatudiant.data.recipe.Recipe;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

public class RecipeAdapter extends BaseAdapter {

    private Context context; //context
    private List<Recipe> items; //data source of the list adapter

    public RecipeAdapter(Context context, List<Recipe> items) {
        this.context = context;
        this.items = items;
        Log.i("eatudiant_debug", "IN LIST VIEW CONSTRUCTOR");

    }

    @Override
    public int getCount() {
        return items.size(); //returns total of items in the list
    }

    @Override
    public Recipe getItem(int position) {
        return items.get(position); //returns list item at the specified position
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Log.i("eatudiant_debug", "IN LIST VIEW 1");

        ViewHolder holder;

        if (view == null) {
            view = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.item_recipe, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }


        Recipe currentItem = getItem(position);
        Log.i("eatudiant_debug", "IN LIST VIEW 2");

        Ion.with(holder.recipeImage).load("https://img.cuisineaz.com/610x610/2015/10/29/i88809-raclette.jpg");
        holder.recipeName.setText(currentItem.getName());
        holder.recipePrice.setText(currentItem.getPrice());
        holder.recipeTime.setText(currentItem.getDuration());
        holder.recipeQuantity.setText(currentItem.getQuantity());

        return view;
    }

    static final class ViewHolder {
        @BindView(R.id.recipeImage)
        ImageView recipeImage;

        @BindView(R.id.recipeName)
        TextView recipeName;

        @BindView(R.id.recipePrice)
        TextView recipePrice;

        @BindView(R.id.recipeTime)
        TextView recipeTime;

        @BindView(R.id.recipeQuantity)
        TextView recipeQuantity;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
