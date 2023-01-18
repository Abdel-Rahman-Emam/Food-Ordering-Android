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

public class OrderHistoryRVAdapter extends RecyclerView.Adapter<OrderHistoryRVAdapter.MyViewHolder> {
    Context mContext;
    ArrayList<OrderModel> orderModels;

    public OrderHistoryRVAdapter(Context context, ArrayList<OrderModel> orderModels) {
        this.orderModels = orderModels;
        this.mContext = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflator = LayoutInflater.from(mContext);
        View view = inflator.inflate(R.layout.recycler_view_row, parent, false);
        return new OrderHistoryRVAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHistoryRVAdapter.MyViewHolder holder, int position) {
        OrderModel order = orderModels.get(position);
        Glide.with(mContext).load(mContext.getResources().getDrawable(R.drawable.ic_baseline_history_24)).placeholder(R.mipmap.ic_launcher).into(holder.resPic);
        holder.resName.setText(order.getResName());
        holder.gateTime.setText(order.getGateTime());
        holder.orderStatus.setText(order.getCurrOrderState().toString());
    }

    @Override
    public int getItemCount() {

        return orderModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView resPic;
        TextView resName;
        TextView orderStatus;
        TextView gateTime;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            resPic = itemView.findViewById(R.id.imageView);
            resName = itemView.findViewById(R.id.restaurantName1);
            orderStatus = itemView.findViewById(R.id.textView);
            gateTime = itemView.findViewById(R.id.restaurantName2);
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
//            itemView.findViewById(R.id.random).setOnClickListener(this);
        }
    }
}
