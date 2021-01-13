package com.example.ecommercenav.Cart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommercenav.Adapter.CartViewItemHolder;
import com.example.ecommercenav.Model.CartModel;
import com.example.ecommercenav.Model.ProductModel;
import com.example.ecommercenav.OderCart.ConfirmOderFinal;
import com.example.ecommercenav.R;
//import com.firebase.ui.database.FirebaseRecyclerAdapter;
//import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
    private ProductModel productModel;

    private int overTotalPrice = 0;
    private int  oneTyprProductPrice = 0;


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
        eventAction();

    }

    private void eventAction() {
        btnCart_ShowNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totalFinal.setText(String.valueOf(overTotalPrice));
                if (overTotalPrice <= 0)
                {
                    //btnCart_ShowNext.setEnabled(false);
                    Toast.makeText(ShowCart.this, "Giỏ hàng chưa có sản phẩm!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(ShowCart.this, ConfirmOderFinal.class);
                    intent.putExtra("Total Price", String.valueOf(overTotalPrice));
                    startActivity(intent);
                    finish();
                }

            }
        });
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
        productModel = new ProductModel();
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("CartList");
        FirebaseRecyclerOptions<CartModel> options = new FirebaseRecyclerOptions.Builder<CartModel>()
                .setQuery(reference.child("UserView")
                        .child(firebaseUser.getUid())
                        .child("Products"), CartModel.class).build();

        FirebaseRecyclerAdapter<CartModel, CartViewItemHolder> adapter = new FirebaseRecyclerAdapter<CartModel, CartViewItemHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewItemHolder holder, int position, @NonNull final CartModel cartModel) {
                holder.item_CartName.setText(cartModel.getP_name());
                holder.item_CartPrice.setText("Giá: " + cartModel.getP_price_final() + " đ");
                holder.item_CartQuantity.setText("Số lượng:[" + cartModel.getP_quantity() + "]");

                oneTyprProductPrice = ((Integer.valueOf(cartModel.getP_discount_price()))) * Integer.valueOf(cartModel.getP_quantity());
                overTotalPrice = overTotalPrice + oneTyprProductPrice;
                totalFinal.setText(String.valueOf(overTotalPrice) + " đ");



                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CharSequence options[] = new CharSequence[]
                                {
                                        "Xóa"
                                };
                        final AlertDialog.Builder builder = new AlertDialog.Builder(ShowCart.this);
                        builder.setTitle("Lựa chọn");
                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog, int which) {
//                                if (which == 0) {
//                                    Intent intent = new Intent(ShowCart.this, UpdateCart.class);
//                                    //intent = getIntent();
//                                    intent.putExtra("id", cartModel.getId());
//                                    intent.putExtra("productID", productModel.getProductID());
//                                    startActivity(intent);
//                                }
                                if (which == 0) {
                                    reference.child("UserView")
                                            .child(firebaseUser.getUid())
                                            .child("Products")
                                            .child(cartModel.getId())
                                            .removeValue()
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(ShowCart.this, "Xóa Thành Công!", Toast.LENGTH_SHORT).show();
//                                                        Intent intent = new Intent(ShowCart.this, MainActivity.class);
//                                                        startActivity(intent);
                                                        oneTyprProductPrice = ((Integer.valueOf(cartModel.getP_discount_price()))) * Integer.valueOf(cartModel.getP_quantity());
                                                        overTotalPrice = overTotalPrice - oneTyprProductPrice;
                                                        totalFinal.setText(String.valueOf(overTotalPrice));
                                                        dialog.dismiss();
                                                    }
                                                }
                                            });
                                }                                                                                    
                            }
                        });
                        builder.show();
                    }
                });
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