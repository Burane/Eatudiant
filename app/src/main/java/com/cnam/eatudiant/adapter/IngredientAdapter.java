package com.cnam.eatudiant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.cnam.eatudiant.R;
import com.cnam.eatudiant.data.recipeDetails.Requirement;
import lombok.Getter;

import java.util.ArrayList;

public class IngredientAdapter extends BaseAdapter {

    private Context context; //context
    @Getter
    private ArrayList<Requirement> items; //data source of the list adapter

    public IngredientAdapter(Context context, ArrayList<Requirement> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size(); //returns total of items in the list
    }

    @Override
    public Requirement getItem(int position) {
        return items.get(position); //returns list item at the specified position
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;

        if (view == null) {
            view = LayoutInflater.from(context).
                    inflate(R.layout.item_ingredient, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }


        Requirement currentItem = getItem(position);
        String qteStr = "" + currentItem.getQuantity();

        holder.ingredientName.setText(currentItem.getName());
        holder.ingredientQty.setText(qteStr);


        return view;
    }

    static final class ViewHolder {

        @BindView(R.id.ingredientName)
        TextView ingredientName;

        @BindView(R.id.ingredientQty)
        TextView ingredientQty;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
