package com.example.ecommercenav.Adapter;

import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommercenav.R;

public class CartViewItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView item_CartName, item_CartQuantity, item_CartPrice;
    private ItemClickListener itemClickListener;

    public CartViewItemHolder(@NonNull View itemView) {
        super(itemView);
        item_CartName = itemView.findViewById(R.id.item_CartName);
        item_CartQuantity = itemView.findViewById(R.id.item_CartQuantity);
        item_CartPrice = itemView.findViewById(R.id.item_CartPrice);
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
