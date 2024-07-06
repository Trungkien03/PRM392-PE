package com.example.foodmanagement.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodmanagement.R;
import com.example.foodmanagement.models.Food;

import java.util.ArrayList;
import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {
    private List<Food> foods = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.food_item, parent, false);
        return new FoodViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        Food currentFood = foods.get(position);
        holder.textViewName.setText(currentFood.getName());
        holder.textViewDescription.setText(currentFood.getDescription());
        Glide.with(holder.itemView.getContext())
                .load(currentFood.getImageUrl())
                .placeholder(R.drawable.ic_food_placeholder)
                .into(holder.foodImage);
    }

    @Override
    public int getItemCount() {
        return foods.size();
    }

    public void setFoods(List<Food> foods) {
        this.foods = foods;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(Food food);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    class FoodViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewName;
        private TextView textViewDescription;
        private ImageView foodImage;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.food_title);
            textViewDescription = itemView.findViewById(R.id.food_description);
            foodImage = itemView.findViewById(R.id.food_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(foods.get(position));
                    }
                }
            });
        }
    }
}
