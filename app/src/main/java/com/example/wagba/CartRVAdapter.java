package com.example.wagba;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CartRVAdapter extends RecyclerView.Adapter<CartRVAdapter.MyViewHolder>{
    Context context;
    ArrayList<CartModel> cartModel;
    public CartRVAdapter(Context context, ArrayList<CartModel> cartModel){
        this.context = context;
        this.cartModel = cartModel;
    }
    @NonNull
    @Override
    public CartRVAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row, parent, false);
        return new CartRVAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartRVAdapter.MyViewHolder holder, int position) {
        holder.dishName.setText(cartModel.get(position).getDishName());
        holder.dishPrice.setText(cartModel.get(position).getDishPrice());
        Glide.with(context).load(cartModel.get(position).getImage()).into(holder.dishPic);
    }

    @Override
    public int getItemCount() {

        return cartModel.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView dishPic;
        TextView dishName;
        TextView dishPrice;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            dishPic = itemView.findViewById(R.id.imageView);
            dishName = itemView.findViewById(R.id.restaurantName1);
            dishPrice = itemView.findViewById(R.id.restaurantName2);
        }
    }
}