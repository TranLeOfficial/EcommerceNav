package com.example.ecommercenav.fragment.search;

import android.content.Context;
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
import com.example.ecommercenav.Filter.FilterSearch;
import com.example.ecommercenav.Model.ProductModel;
import com.example.ecommercenav.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterSearch extends RecyclerView.Adapter<AdapterSearch.HolderSearchProduct> implements Filterable {
    
    private Context context;
    public ArrayList<ProductModel> productList, filterList;
    private FilterSearch filter;

    public AdapterSearch(Context context, ArrayList<ProductModel> productList) {
        this.context = context;
        this.productList = productList;
        this.filterList = productList;
    }

    @NonNull
    @Override
    public HolderSearchProduct onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate layout
        View view = LayoutInflater.from(context).inflate(R.layout.search_item_list, parent, false);
        return new HolderSearchProduct(view);
    }



    @Override
    public void onBindViewHolder(@NonNull HolderSearchProduct holder, int position) {
        //get Data
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

        //set Data
        holder.tvTitleSea.setText(productTitle);
        holder.tvPriceSea.setText(productPrice);
        holder.tvDiscountSea.setText(discountPrice);
        holder.tvDiscountSea.setVisibility(View.VISIBLE);
        try {
            Picasso.with(context).load(productIcon).placeholder(R.drawable.photoload).into(holder.imgSea);
        } catch (Exception e) {
            holder.imgSea.setImageResource(R.drawable.photoload);
        }

        holder.animationSearch.setAnimation(AnimationUtils.loadAnimation(context, R.anim.scale_list));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailsBottomSheet(productModel);
            }
        });

    }

    private void detailsBottomSheet(ProductModel productModel) {
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
        discountPriceD.setText(discountPrice);
        discountPriceD.setVisibility(View.VISIBLE);
        try {
            Picasso.with(context).load(productIcon).placeholder(R.drawable.photoload).into(imgDetail);
        } catch (Exception e) {
            imgDetail.setImageResource(R.drawable.phone);
        }

        btnAddcartD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

    @Override
    public int getItemCount() {
        return productList.size();
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new FilterSearch(this, filterList);
        }
        return filter;
    }

    class HolderSearchProduct extends RecyclerView.ViewHolder {

        private ImageView imgSea;
        private TextView tvDiscountSea, tvTitleSea, tvPriceSea;
        private CardView animationSearch;

        public HolderSearchProduct(@NonNull View itemView) {
            super(itemView);

            animationSearch = itemView.findViewById(R.id.animationSearch);
            imgSea = itemView.findViewById(R.id.imgSea);
            tvDiscountSea = itemView.findViewById(R.id.tvDiscountSea);
            tvTitleSea = itemView.findViewById(R.id.tvTitleSea);
            tvPriceSea = itemView.findViewById(R.id.tvPriceSea);
        }
    }

}
