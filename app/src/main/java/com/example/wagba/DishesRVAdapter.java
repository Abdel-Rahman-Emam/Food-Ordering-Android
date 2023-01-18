package com.example.wagba;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class DishesRVAdapter extends RecyclerView.Adapter<DishesRVAdapter.MyViewHolder>{
    Context mContext;
    ArrayList<DishesModel> dishesModel;
    onDishClick onDishClick;
    public DishesRVAdapter(Context context, ArrayList<DishesModel> dishesModels, onDishClick onDishClick){
        this.mContext = context;
        this.dishesModel = dishesModels;
        this.onDishClick = onDishClick;
    }
    @NonNull
    @Override
    public DishesRVAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.recycler_view_row, parent, false);
        return new DishesRVAdapter.MyViewHolder(view, onDishClick);
    }

    @Override
    public void onBindViewHolder(@NonNull DishesRVAdapter.MyViewHolder holder, int position) {
        holder.dishName.setText(dishesModel.get(position).getDishName());
        holder.dishPrice.setText(dishesModel.get(position).getDishPrice());
        holder.dishDesc.setText(dishesModel.get(position).getDishDesc());
        Glide.with(mContext).load(dishesModel.get(position).getImage()).into(holder.dishPic);
    }

    @Override
    public int getItemCount() {

        return dishesModel.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView dishPic;
        TextView dishPrice;
        TextView dishName;
        TextView dishDesc;

        onDishClick onDishClick;
        ArrayList<CartModel> cartModel;
        public MyViewHolder(@NonNull View itemView, onDishClick onDishClick) {
            super(itemView);
            dishPrice = itemView.findViewById(R.id.restaurantName2);
            dishPic = itemView.findViewById(R.id.imageView);
            dishName = itemView.findViewById(R.id.restaurantName1);
            dishDesc = itemView.findViewById(R.id.textView);
            this.onDishClick = onDishClick;
//            itemView.findViewById(R.id.random).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Bundle cartBundle = new Bundle();
//                    if(dishDesc.getText().toString().equals("In Stock")){
//                        CartModel cartItem = new CartModel(dishName.getText().toString(), dishPrice.getText().toString(), dishPic.toString());
//                        cartModel.add(cartItem);
//                        Toast.makeText(itemView.getContext(), dishName.getText().toString(), Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
            itemView.findViewById(R.id.random).setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onDishClick.onDishClickListener(getAdapterPosition());

        }
    }
    public interface onDishClick{
        void onDishClickListener(int position);
    }
}
