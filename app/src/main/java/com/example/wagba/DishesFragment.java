package com.example.wagba;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.BitSet;


public class DishesFragment extends Fragment implements DishesRVAdapter.onDishClick {
    DatabaseReference database;
    ArrayList<DishesModel> dishes;
    DishesRVAdapter DishesAdapter;
    RecyclerView recyclerView;
    String name;
    String currName;
    int counter= 0;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    public DishesFragment(String name){
        this.name=name;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vw = inflater.inflate(R.layout.fragment_dishes, container, false);
        return vw;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = getView().findViewById(R.id.dishesRView);
        database = FirebaseDatabase.getInstance().getReference("Dishes").child(name);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        dishes = new ArrayList<DishesModel>();
        DishesAdapter= new DishesRVAdapter(getContext(), dishes, this);

        recyclerView.setAdapter(DishesAdapter);
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dishes.clear();
                for( DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if (dataSnapshot.hasChildren()){
                    DishesModel dish = new DishesModel(dataSnapshot.child("dishName").getValue().toString(),
                            dataSnapshot.child("dishDesc").getValue().toString(),
                            dataSnapshot.child("dishPrice").getValue().toString(),
                            dataSnapshot.child("Image").getValue().toString());
                    Log.d("dishprice2",dish.getDishPrice());
                    dishes.add(dish);
                    }
                }
                DishesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    @Override
    public void onDishClickListener(int position) {
        DishesModel currDish =dishes.get(position);
        CartModel cartModel = new CartModel(currDish.dishName, currDish.dishPrice, currDish.Image);
        DatabaseReference database2 = FirebaseDatabase.getInstance().getReference();
        database2.child("carts").child(user.getUid()).child(name).child(Integer.toString(counter)).setValue(cartModel);
        Log.d("dishprice", Boolean.toString(currDish.getDishPrice()==null));
        Toast.makeText(getContext(),"Added "+ currDish.getDishName()+" to cart", Toast.LENGTH_SHORT).show();
        SharedPreferences sp = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("name", name);
        editor.apply();
        Snackbar sb = Snackbar.make(getView().getRootView().findViewById(R.id.container), "Added to cart", Snackbar.LENGTH_SHORT);
        sb.setAction("Cart", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                Bundle bundle = new Bundle();
                CartFragment cf = new CartFragment();
                bundle.putString("name", name);
                cf.setArguments(bundle);

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.replaceableLayout, cf).addToBackStack("2");
                fragmentTransaction.commit();
            }
        });
        sb.show();
        counter++;
    }
}