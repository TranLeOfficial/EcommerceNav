package com.example.ecommercenav.fragment.search;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommercenav.Model.ProductModel;
import com.example.ecommercenav.R;
import com.example.ecommercenav.fragment.home.AdapterProducts;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    private SearchViewModel searchViewModel;
    private RecyclerView recyListSearch;
    private ArrayList<ProductModel> productModels;
    private AdapterSearch adapterSearch;
    private EditText   edtSearch;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        View root = inflater.inflate(R.layout.fragment_search, container, false);
        final TextView textView = root.findViewById(R.id.text_gallery);
        recyListSearch = root.findViewById(R.id.recyListSearch);
        edtSearch = root.findViewById(R.id.edtSearch);


        loadAllProducts();

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try{
                    adapterSearch.getFilter().filter(s);
                }   catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


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
                adapterSearch = new AdapterSearch(getContext(), productModels);
                //GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL, false);
                LinearLayoutManager gridLayoutManager = new LinearLayoutManager(getContext());
                gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyListSearch.setLayoutManager(gridLayoutManager);
                recyListSearch.setAdapter(adapterSearch);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}