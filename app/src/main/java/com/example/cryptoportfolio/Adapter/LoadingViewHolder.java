package com.example.cryptoportfolio.Adapter;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import com.example.cryptoportfolio.R;

public class LoadingViewHolder extends RecyclerView.ViewHolder{

    public ProgressBar progressBar;

    public LoadingViewHolder(View itemView) {
        super(itemView);
        progressBar = (ProgressBar)itemView.findViewById(R.id.progress_bar);
    }
}
