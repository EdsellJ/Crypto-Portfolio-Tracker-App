package com.example.cryptoportfolio;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class CoinAdapter extends RecyclerView.Adapter<CoinAdapter.MyViewHolder> {

    private Context mContext;
    private List<CoinModel> mData;

    public CoinAdapter(Context mContext, List<CoinModel> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Returns coin item view
        View v;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        v = inflater.inflate(R.layout.coin_item, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // Bind text variables
        holder.name.setText(mData.get(position).getName());
        holder.price.setText(mData.get(position).getPrice());

        // Display image
        Glide.with(mContext)
                .load(mData.get(position).getImg())
                .into(holder.img);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // Connect screen elements
        TextView name;
        TextView price;
        ImageView img;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name_txt);
            price = itemView.findViewById(R.id.price_txt);
            img = itemView.findViewById(R.id.imageView);
        }
    }
}
