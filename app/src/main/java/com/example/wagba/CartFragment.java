package com.example.wagba;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;


public class CartFragment extends Fragment {
    ArrayList<CartModel> cartModels = new ArrayList<>();
    RecyclerView recyclerView;
    ImageButton imageButton;
    Button confirmBtn;
    CartRVAdapter RVAdapter;
    DatabaseReference cartsDB;
    DatabaseReference orderDB;
    TextView price;
    FirebaseUser user;
    String name;
    int totprice = 0;
    Bundle bundle;
    boolean orderValid = false;
    String gateTime = "Gate 3, 12 PM";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vw = inflater.inflate(R.layout.fragment_cart, container, false);
        bundle = getArguments();
        if(bundle!=null)
            name = bundle.getString("name");
        return vw;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        imageButton = getView().findViewById(R.id.empty);
        confirmBtn = getView().findViewById(R.id.confirm_button);
        user = FirebaseAuth.getInstance().getCurrentUser();
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = getView().findViewById(R.id.cartRView);
        price = getView().findViewById(R.id.price);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        RVAdapter = new CartRVAdapter(getContext(), cartModels);
        recyclerView.setAdapter(RVAdapter);
        cartsDB = FirebaseDatabase.getInstance().getReference("carts").child(user.getUid());
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        if(sharedPref.contains("name")) name = sharedPref.getString("name", "");
        if(name == null)
            Toast.makeText(getContext(), "cart is empty", Toast.LENGTH_SHORT).show();
        else {
            cartsDB = cartsDB.child(name);
            cartsDB.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    cartModels.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        CartModel cart = new CartModel(dataSnapshot.child("dishName").getValue().toString(),
                                dataSnapshot.child("dishPrice").getValue().toString(),
                                dataSnapshot.child("image").getValue().toString());
                        totprice += Integer.parseInt(dataSnapshot.child("dishPrice").getValue().toString().split(" ")[0]);
                        cartModels.add(cart);
                    }
                    price.setText(totprice + " EGP");
                    RVAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }


            });
        }
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartsDB.removeValue();
                totprice = 0;
            }
        });
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar rn = Calendar.getInstance();
                int hour = rn.get(Calendar.HOUR_OF_DAY);
                String[] gateTimes = {"Gate 3, 12 PM","Gate 3, 3 PM","Gate 4, 12 PM","Gate 4, 3 PM"};
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Confirmation");
                builder.setSingleChoiceItems(gateTimes, 1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        gateTime = gateTimes[i];

                        if((hour>=10 && gateTime.contains("12 PM"))) {
                            Toast.makeText(getContext(), "Your order time is invalid", Toast.LENGTH_SHORT).show();
                            orderValid = false;
                        }
                        else if (hour>=13 && gateTime.contains("3 PM")) {
                            Toast.makeText(getContext(), "Your order time is invalid", Toast.LENGTH_SHORT).show();
                            orderValid = false;
                        }
                        else {
                            orderValid = true;
                            Toast.makeText(getContext(), "Your order will be at " + gateTime, Toast.LENGTH_SHORT).show();
                        }
                        //Toast.makeText(getContext(), "Your order will be at"+gateTime, Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        orderDB = FirebaseDatabase.getInstance().getReference("orders").child(user.getUid());
                        if(orderValid)
                            orderDB.push().setValue(new OrderModel(gateTime, name, OrderModel.orderState.WaitingForConfirmation));
                        else
                            Toast.makeText(getContext(), "Your order time is invalid", Toast.LENGTH_SHORT).show();
                        dialogInterface.dismiss();
                    }
                });
                builder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
            }
        });


    }
}