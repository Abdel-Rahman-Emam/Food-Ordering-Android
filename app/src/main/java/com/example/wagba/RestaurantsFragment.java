package com.example.wagba;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class RestaurantsFragment extends Fragment implements RestaurantsRVAdapter.onRestaurantClick{
    RecyclerView rv;
    DatabaseReference database;
    RestaurantsRVAdapter ResAdapter;
    ArrayList<RestaurantsModel> restaurants;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vw = inflater.inflate(R.layout.fragment_restaurants, container, false);

        return vw;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //setUpRestaurants();
        rv = getView().findViewById(R.id.restaurantRView);
        database = FirebaseDatabase.getInstance().getReference("Restaurant");
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        DatabaseReference cartsDB = FirebaseDatabase.getInstance().getReference("carts").child(user.getUid());
        cartsDB.removeValue();
        restaurants = new ArrayList<RestaurantsModel>();
        ResAdapter = new RestaurantsRVAdapter(getContext(), restaurants, this);
        rv.setAdapter(ResAdapter);
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                restaurants.clear();
                for( DataSnapshot dataSnapshot : snapshot.getChildren()){
                    RestaurantsModel restaurant = dataSnapshot.getValue(RestaurantsModel.class);
                    restaurants.add(restaurant);
                }
                ResAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onRestaurantClickListener(int position) {
        RestaurantsModel currRes = restaurants.get(position);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        Log.d("somethingsomething", String.valueOf(fragmentManager));
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.replaceableLayout, new DishesFragment(currRes.restaurantName)).addToBackStack("1");
        fragmentTransaction.commit();
    }
/*
    private void setUpRestaurants(){

        String[] restaurantsName = getResources().getStringArray(R.array.restaurants);
        String restaurantStatusOpen = getResources().getString(R.string.statusOpen);
        String restaurantStatusClosed = getResources().getString(R.string.statusClosed);
        for(int i = 0; i<restaurantsName.length; i++){
            Log.d("idk",Integer.toString(restaurantsName.length));
            restaurantsModels.add(new RestaurantsModel(restaurantsName[i], restaurantStatusOpen, restaurantImages[i]));
        }
    }*/
}