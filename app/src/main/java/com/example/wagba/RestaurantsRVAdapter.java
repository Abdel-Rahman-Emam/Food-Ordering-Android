package com.example.wagba;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class RestaurantsRVAdapter extends RecyclerView.Adapter<RestaurantsRVAdapter.MyViewHolder> {
        Context mContext;
        ArrayList<RestaurantsModel> restaurantsModels;
        onRestaurantClick onResClick;

        public  RestaurantsRVAdapter(Context context, ArrayList<RestaurantsModel> restaurantsModels, onRestaurantClick onResClick) {
            this.restaurantsModels = restaurantsModels;
            this.mContext = context;
            this.onResClick = onResClick;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflator = LayoutInflater.from(mContext);
            View view = inflator.inflate(R.layout.recycler_view_row, parent, false);
            return new RestaurantsRVAdapter.MyViewHolder(view, onResClick);
        }

        @Override
        public void onBindViewHolder(@NonNull RestaurantsRVAdapter.MyViewHolder holder, int position) {
            RestaurantsModel restaurant = restaurantsModels.get(position);
            Glide.with(mContext).load(restaurantsModels.get(position).getRestaurantImage()).into(holder.resPic);
            holder.resName.setText(restaurant.getRestaurantName());
            holder.resStat.setText(restaurant.getRestaurantStatus());

        }

        @Override
        public int getItemCount() {

            return restaurantsModels.size();
        }

        public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            ImageView resPic;
            TextView resName;
            TextView resStat;
            onRestaurantClick onResClick;
            public MyViewHolder(@NonNull View itemView, onRestaurantClick onResClick) {
                super(itemView);
                resPic = itemView.findViewById(R.id.imageView);
                resName = itemView.findViewById(R.id.restaurantName1);
                resStat = itemView.findViewById(R.id.textView);
                this.onResClick = onResClick;
//                itemView.findViewById(R.id.random).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                        Toast.makeText(itemView.getContext(), resName.getText().toString(), Toast.LENGTH_SHORT).show();
//                        Log.d("toast",resName.getText().toString());
//                        AppCompatActivity activity=(AppCompatActivity)view.getContext();
//                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.replaceableLayout,new DishesFragment(resName.getText().toString())).addToBackStack(null).commit();
//                    }
//                });
                itemView.findViewById(R.id.random).setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                onResClick.onRestaurantClickListener(getAdapterPosition());
            }
        }
        public interface onRestaurantClick{
            void onRestaurantClickListener(int position);
        }
    }
