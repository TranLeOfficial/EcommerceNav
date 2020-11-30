package com.example.ecommercenav.fragment.infoShop;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.ecommercenav.R;

public class InfoShopFragment extends Fragment {

    private InfoShopViewModel infoShopViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        infoShopViewModel =
                ViewModelProviders.of(this).get(InfoShopViewModel.class);
        View root = inflater.inflate(R.layout.fragment_infoshop, container, false);
        final TextView textView = root.findViewById(R.id.text_slideshow);
        infoShopViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}