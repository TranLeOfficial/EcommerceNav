package com.example.ecommercenav.Cart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.ecommercenav.Model.CartModel;
import com.example.ecommercenav.Model.ProductModel;
import com.example.ecommercenav.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class UpdateCart extends AppCompatActivity {

    private ImageView cart_Icon;
    private TextView cart_Discount, cart_Price, cart_DiscountPrice,
            cart_PriceFinal, cart_Name, cart_Description;
    private ElegantNumberButton cart_Cong_Tru;
    private Button cart_Add_Btn;

    private String id = "";

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    private ProductModel productModel;
    private CartModel cartModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_cart_layout);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
        actionBar.setTitle("Cập Nhật Sản Phẩm");
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        AnhXa();

        loadCartDetails();
        //getCartDetails(id);
        eventAction();

    }

    private void loadCartDetails() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("ListCart");
        reference.child("UserView").child(firebaseUser.getUid()).child("Products").child(id)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String sCart_Name = "" + snapshot.child("p_name").getValue();
                        String sCart_PriceFinal = "" + snapshot.child("p_price_final").getValue();
                        String sCart_Discount = "" + snapshot.child("p_discount").getValue();
                        String sCart_DiscountPrice = "" + snapshot.child("p_discount_price").getValue();
                        String sCart_Price = "" + snapshot.child("p_price").getValue();
                        String sCart_Quantity = "" + snapshot.child("p_quantity").getValue();
                        String sCart_Time = "" + snapshot.child("p_time").getValue();
                        String sCart_Date = "" + snapshot.child("p_date").getValue();
                        String productIcon = "" + snapshot.child("productIcon").getValue();
                        String id = "" + snapshot.child("id").getValue();

                        //setData to views

                        cart_Name.setText(sCart_Name);
                        cart_Discount.setText(sCart_Discount);
                        cart_PriceFinal.setText(sCart_PriceFinal);
                        cart_DiscountPrice.setText(sCart_DiscountPrice);
                        cart_Price.setText(sCart_Price);
                        

                        
                        try {
                            Picasso.with(getApplicationContext()).load(productIcon).placeholder(R.drawable.bg_profile).into(cart_Icon);
                        } catch (Exception e) {
                            cart_Icon.setImageResource(R.drawable.bg_profile);
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private int cost = 0;
    private int finalCost = 0;





    private void eventAction() {
        cart_Add_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addingToCartList();
                Snackbar.make(view, "Them Gio Hang Thanh Cong", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private String saveCurrentTime, saveCurrentDate;

    private void addingToCartList() {
        Calendar calendarForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendarForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendarForDate.getTime());

        final DatabaseReference referenceCart = FirebaseDatabase.getInstance().getReference().child("CartList");

        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("id", id);
        cartMap.put("p_name", cart_Name.getText().toString().trim());
        cartMap.put("p_discount", productModel.getDiscountPrice());
        cartMap.put("p_quantity", cart_Cong_Tru.getNumber());
        cartMap.put("p_price", cart_Price.getText().toString().trim());
        cartMap.put("p_discount_price", cart_DiscountPrice.getText().toString().trim());
        cartMap.put("p_price_final", cart_PriceFinal.getText().toString().trim());
        cartMap.put("p_date", saveCurrentDate);
        cartMap.put("p_time", saveCurrentTime);


        referenceCart.child("UserView").child(firebaseUser.getUid())
                .child("Products").child(id)
                .updateChildren(cartMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            referenceCart.child("AdminView").child(firebaseUser.getUid())
                                    .child("Products").child(id)
                                    .updateChildren(cartMap)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful())
                                            {
                                                Toast.makeText(UpdateCart.this, "Them Gio Hang Thanh Cong ", Toast.LENGTH_SHORT).show();
                                                finish();
                                            }
                                        }
                                    });
                        }
                    }
                });
    }




    private void getCartDetails(final String id) {


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("ListCart");
        reference.child("UserView").child(firebaseUser.getUid()).child("Products").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    productModel = dataSnapshot.getValue(ProductModel.class);
                    CartModel cartModel = dataSnapshot.getValue(CartModel.class);
                    //
                    cart_Name.setText(cartModel.getP_name());
                    cart_Description.setText("-----------------------HELLO------------------------");
                    cart_Discount.setText(cartModel.getP_discount()+"% OFF");
                    cart_Price.setText(cartModel.getP_price()+"");
                    Picasso.with(UpdateCart.this).load(productModel.getProductIcon()).into(cart_Icon);
                    int a = Integer.parseInt(productModel.getProductPrice());
                    int b = Integer.parseInt(productModel.getDiscountPrice());
                    cost = a -((a * b) / 100);
                    cart_Price.setPaintFlags(cart_Price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                    cart_DiscountPrice.setText(cost+"");
                    cart_PriceFinal.setText(cartModel.getP_price_final()+"");
                    int d = Integer.parseInt(cartModel.getP_quantity());
                    cart_Cong_Tru.setNumber(d+"");
                    cart_Cong_Tru.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
                        @Override
                        public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                            final String count = cart_Cong_Tru.getNumber();
                            final int c = Integer.parseInt(count);
                            finalCost =  cost * c;
                            cart_PriceFinal.setText(finalCost+"");
                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void AnhXa() {
        id = getIntent().getStringExtra("id");
        
        cart_Icon = findViewById(R.id.cart_Icon);
        cart_Discount = findViewById(R.id.cart_Discount);
        cart_Price = findViewById(R.id.cart_Price);
        cart_DiscountPrice = findViewById(R.id.cart_DiscountPrice);
        cart_PriceFinal = findViewById(R.id.cart_PriceFinal);
        cart_Description = findViewById(R.id.cart_Description);
        cart_Name = findViewById(R.id.cart_Name);
        cart_Cong_Tru = findViewById(R.id.cart_Cong_Tru);
        cart_Add_Btn = findViewById(R.id.cart_Add_Btn);
    }


}