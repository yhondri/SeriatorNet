package com.seriatornet.yhondri.seriatornet.Module.Main.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.seriatornet.yhondri.seriatornet.Model.DataBase.Show.Show;
import com.seriatornet.yhondri.seriatornet.R;

import java.util.List;

public class DiscoverCategoriesAdapter extends RecyclerView.Adapter<DiscoverCategoriesAdapter.ViewHolder> {

    private String[] categories;
    private Context context;

    public DiscoverCategoriesAdapter(String[] categories, Context context) {
        this.categories = categories;
        this.context = context;
    }

    @Override
    public DiscoverCategoriesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_show_category, parent, false);
        DiscoverCategoriesAdapter.ViewHolder viewHolder = new DiscoverCategoriesAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DiscoverCategoriesAdapter.ViewHolder holder, int position) {
        String categoryName = categories[position];
        holder.categoryNameTextView.setText(categoryName);
    }

    @Override
    public int getItemCount() {
        return categories.length;
    }

    public String getItemAt(int position) {
        return categories[position];
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView categoryNameTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.discover_cardview);
            categoryNameTextView = itemView.findViewById(R.id.showCategoryTextView);
        }
    }
}
