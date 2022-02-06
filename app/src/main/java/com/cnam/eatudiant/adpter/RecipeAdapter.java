package com.cnam.eatudiant.adpter;

import android.content.Context;
import android.net.Uri;
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

    private Context context; //context
    private List<Recipe> items; //data source of the list adapter

    public RecipeAdapter(Context context, List<Recipe> items) {
        this.context = context;
        this.items = items;

        View v = LayoutInflater.from(context).inflate(R.layout.item_recipe, null);
        ButterKnife.bind(this, v);
    }

    @Override
    public int getCount() {
        return items.size(); //returns total of items in the list
    }

    @Override
    public Object getItem(int position) {
        return items.get(position); //returns list item at the specified position
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // inflate the layout for each list row
        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.item_recipe, parent, false);
        }

        // get current item to be displayed
        Recipe currentItem = (Recipe) getItem(position);
        ButterKnife.bind(this, convertView);


        //sets the text for item name and item description from the current item object
        Ion.with(recipeImage).load("https://img.cuisineaz.com/610x610/2015/10/29/i88809-raclette.jpg");
        recipeName.setText(currentItem.getName());
        recipePrice.setText(currentItem.getPrice());
        recipeTime.setText(currentItem.getDuration());
        recipeQuantity.setText(currentItem.getQuantity());

        // returns the view for the current row
        return convertView;
    }
}
