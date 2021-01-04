package com.example.ecommercenav.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ecommercenav.Adapter.CartViewItemHolder;
import com.example.ecommercenav.Model.CartModel;
import com.example.ecommercenav.R;
//import com.firebase.ui.database.FirebaseRecyclerAdapter;
//import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ShowCart extends AppCompatActivity {

    private TextView totalFinal;
    private RecyclerView cart_List;
    private RecyclerView.LayoutManager layoutManager;
    private Button btnCart_ShowNext;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_cart_layout);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
        actionBar.setTitle("Giỏ Hàng");
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        AnhXa();
    }

    private void AnhXa() {
        layoutManager = new LinearLayoutManager(this);
        totalFinal = findViewById(R.id.totalFinal);
        cart_List = findViewById(R.id.cart_List);
        cart_List.setHasFixedSize(true);
        cart_List.setLayoutManager(layoutManager);
        btnCart_ShowNext = findViewById(R.id.btnCart_ShowNext);

    }

    @Override
    protected void onStart() {
        super.onStart();
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("CartList");
        FirebaseRecyclerOptions<CartModel> options = new FirebaseRecyclerOptions.Builder<CartModel>()
                .setQuery(reference.child("UserView")
                        .child(firebaseUser.getUid())
                        .child("Products"), CartModel.class).build();

        FirebaseRecyclerAdapter<CartModel, CartViewItemHolder> adapter = new FirebaseRecyclerAdapter<CartModel, CartViewItemHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewItemHolder holder, int position, @NonNull CartModel cartModel) {
                holder.item_CartName.setText(cartModel.getP_name());
                holder.item_CartPrice.setText(cartModel.getP_price_final());
                holder.item_CartQuantity.setText("["+cartModel.getP_quantity()+"]");
            }

            @NonNull
            @Override
            public CartViewItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart_show, parent, false);
                CartViewItemHolder holder = new CartViewItemHolder(view);
                return holder;
            }
        };

        cart_List.setAdapter(adapter);
        adapter.startListening();

    }

}