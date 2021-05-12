package com.example.cryptoportfolio.Adapter;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import com.example.cryptoportfolio.R;

public class LoadingViewHolder extends RecyclerView.ViewHolder{

    // Loading layout shows progress bar when pulled to refresh

    public ProgressBar progressBar;

    public LoadingViewHolder(View itemView) {
        super(itemView);
        progressBar = itemView.findViewById(R.id.progress_bar);
    }
}
