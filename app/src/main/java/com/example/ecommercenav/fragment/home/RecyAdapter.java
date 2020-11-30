package com.example.ecommercenav.fragment.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommercenav.R;

import java.util.List;

public class RecyAdapter extends RecyclerView.Adapter<RecyAdapter.ViewHolder> {
    
    public class ViewHolder extends  RecyclerView.ViewHolder{
        TextView titles, prices;
        ImageView gridImages;

        public  ViewHolder(View itemView){
            super(itemView);
            prices = itemView.findViewById(R.id.tvPriceRecycler);
            titles = itemView.findViewById(R.id.tvNameRecycler);
            gridImages = itemView.findViewById(R.id.imageRecyclerview);
        }
    }

    List<String> prices;
    List<String> titles;
    List<Integer> images;
    LayoutInflater inflater;

    public RecyAdapter(Context context, List<String> titles, List<Integer> images, List<String> prices)
    {
        this.prices = prices;
        this.titles = titles;
        this.images = images;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recyclerview_model, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.titles.setText(titles.get(position));
        holder.prices.setText(prices.get(position));
        holder.gridImages.setImageResource(images.get(position));
        
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }



}
