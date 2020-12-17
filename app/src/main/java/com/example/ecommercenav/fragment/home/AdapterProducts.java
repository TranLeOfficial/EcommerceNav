package com.example.ecommercenav.fragment.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommercenav.Filter.FilterProduct;
import com.example.ecommercenav.Model.ProductModel;
import com.example.ecommercenav.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

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
        holder.discountPriceS.setText(discountPrice);
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
            filter = new FilterProduct(this, filterList);
        }
        return filter;
    }


    class HolderProducts extends RecyclerView.ViewHolder {

        private ImageView imageRecyclerview;
        private TextView tvPriceRecycler;
        private TextView tvNameRecycler;
        private TextView discountPriceS;


        public HolderProducts(@NonNull View itemView) {
            super(itemView);

            imageRecyclerview = itemView.findViewById(R.id.imageRecyclerview);
            tvPriceRecycler = itemView.findViewById(R.id.tvPriceRecycler);
            tvNameRecycler = itemView.findViewById(R.id.tvNameRecycler);
            discountPriceS = itemView.findViewById(R.id.discountPriceS);
        }
    }
}

