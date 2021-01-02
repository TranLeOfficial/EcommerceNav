package com.example.ecommercenav.fragment.home;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommercenav.Filter.FilterProduct;
import com.example.ecommercenav.Model.ProductModel;
import com.example.ecommercenav.Model.ProfileModel;
import com.example.ecommercenav.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import p32929.androideasysql_library.Column;
import p32929.androideasysql_library.EasyDB;

public class AdapterProducts extends RecyclerView.Adapter<AdapterProducts.HolderProducts> implements Filterable {

    private Context context;
    public ArrayList<ProductModel> productList, filterList;
    private FilterProduct filter;


    public AdapterProducts(Context context, ArrayList<ProductModel> productList) {
        this.context = context;
        this.productList = productList;
        this.filterList = productList;
    }

    @NonNull
    @Override
    public HolderProducts onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate layout
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_model, parent, false);
        return new HolderProducts(view);
    }


    @Override
    public void onBindViewHolder(@NonNull HolderProducts holder, int position) {
        //get data
        final ProductModel productModel = productList.get(position);

        String id = productModel.getProductID();
        String uid = productModel.getUid();
        String discountAvailable = productModel.getDiscountAvailable();
        String discountPrice = productModel.getDiscountPrice();
        String productTitle = productModel.getProductTitle();
        String productQuantity = productModel.getProductQuantity();
        String productCategory = productModel.getProductCategory();
        String productDescription = productModel.getProductDescription();
        String productPrice = productModel.getProductPrice();
        String productIcon = productModel.getProductIcon();
        String stimetamp = productModel.getTimestamp();

        //set data
        holder.tvNameRecycler.setText(productTitle);
        holder.tvPriceRecycler.setText(productPrice);
        holder.discountPriceS.setText(discountPrice + "% OFF");
        holder.discountPriceS.setVisibility(View.VISIBLE);
//        if(discountAvailable.equals("true"))
//        {
//            //product is discount
//            holder.discountPriceS.setVisibility(View.VISIBLE);
//        }
//        else {
//            holder.discountPriceS.setVisibility(View.GONE);
//        }
        try {
            Picasso.with(context).load(productIcon).placeholder(R.drawable.banner_ao).into(holder.imageRecyclerview);
        } catch (Exception e) {
            holder.imageRecyclerview.setImageResource(R.drawable.banner_giay);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show item details
                Toast.makeText(context, "Hello", Toast.LENGTH_SHORT).show();
                detailsBottomSheet(productModel);
            }
        });

    }

    private void detailsBottomSheet(final ProductModel productModel) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        //inflate view for bottomsheet
        View view = LayoutInflater.from(context).inflate(R.layout.detail_product, null);
        //set view to bottom sheet
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();

        //init views of bottomsheet
        ImageButton btnBack = view.findViewById(R.id.btnBack);
        Button btnRatingD = view.findViewById(R.id.btnRantingD);
        Button btnAddcartD = view.findViewById(R.id.btnAddcartD);
        ImageView imgDetail = view.findViewById(R.id.imgDetail);
        TextView productNameD = view.findViewById(R.id.productNameD);
        TextView productPriceD = view.findViewById(R.id.productPriceD);
        TextView productDescriptionD = view.findViewById(R.id.productDescriptionD);
        TextView productCategoryD = view.findViewById(R.id.productCategoryD);
        TextView productQuantityD = view.findViewById(R.id.productQuantityD);
        TextView discountPriceD = view.findViewById(R.id.discountPriceD);


        //getData
        final String id = productModel.getProductID();
        String uid = productModel.getUid();
        String discountAvailable = productModel.getDiscountAvailable();
        String discountPrice = productModel.getDiscountPrice();
        final String productTitle = productModel.getProductTitle();
        String productQuantity = productModel.getProductQuantity();
        String productCategory = productModel.getProductCategory();
        String productDescription = productModel.getProductDescription();
        String productPrice = productModel.getProductPrice();
        String productIcon = productModel.getProductIcon();
        final String stimetamp = productModel.getTimestamp();

        //setData
        productNameD.setText("Tên sản phẩm: " + productTitle);
        productPriceD.setText("Giá sản phẩm: " + productPrice);
        productDescriptionD.setText("Mô tả: " + productDescription);
        productCategoryD.setText("Danh mục: " + productCategory);
        productQuantityD.setText("Số lượng: " + productQuantity);
        discountPriceD.setText(discountPrice + "% OFF");
        discountPriceD.setVisibility(View.VISIBLE);
        try {
            Picasso.with(context).load(productIcon).placeholder(R.drawable.photoload).into(imgDetail);
        } catch (Exception e) {
            imgDetail.setImageResource(R.drawable.phone);
        }

        btnAddcartD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showQuantityDialog(productModel);
                Toast.makeText(context, "Them Gio Hang", Toast.LENGTH_SHORT).show();
            }
        });


        //back btn
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });


    }

    private int cost = 0;
    private int finalCost = 0;
    private int quantity = 0;

    private void showQuantityDialog(final ProductModel productModel) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_addcart, null);
        //init layout views
        ImageView cartIcon = view.findViewById(R.id.cartIcon);
        TextView cartDiscount = view.findViewById(R.id.cartDiscount);
        final TextView cartTitle = view.findViewById(R.id.cartTitle);
        TextView cartPrice = view.findViewById(R.id.cartPrice);
        final TextView cartDiscountPrice = view.findViewById(R.id.cartDiscountPrice);
        final TextView cartPricePlus = view.findViewById(R.id.cartPricePlus);
        final TextView cartQuantityCong_Tru = view.findViewById(R.id.cartQuantityCong_Tru);
        ImageButton cartIncrement = view.findViewById(R.id.cartIncrement);
        ImageButton cartDecrement = view.findViewById(R.id.cartDecrement);
        Button addToCartBtn = view.findViewById(R.id.addToCartBtn);

        //load data from model
        final String productID = productModel.getProductID();
        String uid = productModel.getUid();
        String discountAvailable = productModel.getDiscountAvailable();
        String discountPrice = productModel.getDiscountPrice();
        final String productTitle = productModel.getProductTitle();
        String productQuantity = productModel.getProductQuantity();
        String productCategory = productModel.getProductCategory();
        String productDescription = productModel.getProductDescription();
        String productPrice = productModel.getProductPrice();
        String productIcon = productModel.getProductIcon();
        final String stimetamp = productModel.getTimestamp();
        int a = Integer.parseInt(productModel.getProductPrice());
        int b = Integer.parseInt(productModel.getDiscountPrice());
        cost = a-((a*b)/100);
        //getData
        int sPrice;
        sPrice = cost;
        cartPrice.setPaintFlags(cartPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        finalCost = sPrice;
        quantity = 1;

        //dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view);
        try {
            Picasso.with(context).load(productIcon).placeholder(R.drawable.cart).into(cartIcon);

        } catch (Exception e) {
            cartIcon.setImageResource(R.drawable.cart);
        }

        cartTitle.setText("" + productTitle);
        cartDiscount.setText("" + Integer.parseInt(discountPrice) +"% OFF");
        cartQuantityCong_Tru.setText("" + quantity);
        cartPrice.setText("" + productModel.getProductPrice());
        cartDiscountPrice.setText("" + cost);
        cartPricePlus.setText("" + finalCost + " VND");

        final AlertDialog dialog = builder.create();
        dialog.show();
//
        cartIncrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalCost = finalCost + cost;
                quantity++;

                cartPricePlus.setText(finalCost +  " VND");
                cartQuantityCong_Tru.setText("" + quantity);
            }
        });
        cartDecrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity > 1) {
                    finalCost = finalCost - cost;
                    quantity--;
                    cartPricePlus.setText(finalCost+ " VND");
                    cartQuantityCong_Tru.setText("" + quantity);
                }
            }
        });
        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = cartTitle.getText().toString().trim();
                String priceDiscount = cartDiscountPrice.getText().toString().trim().replace("VND","");
                String price = cartPricePlus.getText().toString().trim();
                String quantity_plus= cartQuantityCong_Tru.getText().toString().trim().replace("VND", "");
                addToCart(productID, title, priceDiscount, price, quantity_plus);
                dialog.dismiss();
            }
        });
    }

    private int itemId = 1;

    private void addToCart(String productID, String title, String priceDiscount, String price, String quantity_plus) {
        itemId++;
        EasyDB easyDB = EasyDB.init(context, "ITEMS_DB")
                .setTableName("ITEMS_TABLE")
                .addColumn(new Column("Item_Id", new String[]{"text", "unique"}))
                .addColumn(new Column("Item_PID", new String[]{"text", "not null"}))
                .addColumn(new Column("Item_Name", new String[]{"text", "not null"}))
                .addColumn(new Column("Item_Price_Each", new String[]{"text", "not null"}))
                .addColumn(new Column("Item_Price", new String[]{"text", "not null"}))
                .addColumn(new Column("Item_Quantity", new String[]{"text", "not null"}))
                .doneTableColumn();
        Boolean b = easyDB.addData("Item_Id", itemId)
                .addData("Item_PID", productID)
                .addData("Item_Name", title)
                .addData("Item_Price_Each", priceDiscount)
                .addData("Item_Price", price)
                .addData("Item_Quantity", quantity_plus)
                .doneDataAdding();
        Toast.makeText(context, "Addtocart...", Toast.LENGTH_SHORT).show();
    }


    @Override
    public int getItemCount() {
        return productList.size();
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new FilterProduct(this, filterList);
        }
        return filter;
    }


    class HolderProducts extends RecyclerView.ViewHolder {

        private ImageView imageRecyclerview;
        private TextView tvPriceRecycler;
        private TextView tvNameRecycler;
        private TextView discountPriceS;
        private CardView cardView;


        public HolderProducts(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardView);
            imageRecyclerview = itemView.findViewById(R.id.imageRecyclerview);
            tvPriceRecycler = itemView.findViewById(R.id.tvPriceRecycler);
            tvNameRecycler = itemView.findViewById(R.id.tvNameRecycler);
            discountPriceS = itemView.findViewById(R.id.discountPriceS);
        }
    }
}

