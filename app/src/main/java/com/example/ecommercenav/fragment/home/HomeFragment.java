package com.example.ecommercenav.fragment.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommercenav.Model.ProductModel;
import com.example.ecommercenav.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private ViewFlipper viewFlipper;
    private HomeViewModel homeViewModel;
    private Button button;
    //    RecyclerView
    private RecyclerView recyListData;
    private ArrayList<ProductModel> productModels;
    private AdapterProducts adapterProducts;
//    private List<String> titles;
//    private List<String> prices;
//    private List<Integer> images;

  

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        viewFlipper = root.findViewById(R.id.viewLipper);
//        Recyclerview
        recyListData = root.findViewById(R.id.recyListData);
//        titles = new ArrayList<>();
//        images = new ArrayList<>();
//        prices = new ArrayList<>();
//        titles.add("Hello World");
//        titles.add("Hello My");
//        titles.add("Hello My");
//
//        prices.add("180000");
//        prices.add("180000");
//        prices.add("180000");
//
//        images.add(R.drawable.banner_ao);
//        images.add(R.drawable.banner_ao);
//        images.add(R.drawable.banner_giay);
//
//        recyAdapter = new RecyAdapter(getContext(), titles, images, prices);
        loadAllProducts();

        

//        Banner
        int slides[] = {R.drawable.banner_phone, R.drawable.banner_ao, R.drawable.banner_giay};
        for (int slide : slides) {
            ActionViewFlipper(slide);
        }
        return root;
    }

    private void loadAllProducts() {
        productModels = new ArrayList<>();
        //get all products
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Products");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //clear productList
                productModels.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    ProductModel productModel = ds.getValue(ProductModel.class);
                    productModels.add(productModel);
                }
                //setupAdapter
                adapterProducts = new AdapterProducts(getContext(), productModels);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
//                LinearLayoutManager gridLayoutManager = new LinearLayoutManager(Trangchu_Admin.this);
//                gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyListData.setLayoutManager(gridLayoutManager);
                recyListData.setAdapter(adapterProducts);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void ActionViewFlipper(int images) {
        ImageView imageView = new ImageView(getContext());
        imageView.setImageResource(images);
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        viewFlipper.addView(imageView);
        Animation animationSlideIn = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_right);
        Animation animationSlideOut = AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_right);
        viewFlipper.setInAnimation(animationSlideIn);
        viewFlipper.setOutAnimation(animationSlideOut);

    }
}