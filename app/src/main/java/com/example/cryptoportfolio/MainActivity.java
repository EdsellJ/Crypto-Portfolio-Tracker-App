package com.example.cryptoportfolio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.widget.Toast;

import com.example.cryptoportfolio.Adapter.CoinAdapter;
import com.example.cryptoportfolio.Interface.ILoadMore;
import com.example.cryptoportfolio.Model.CoinModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    List<CoinModel> items = new ArrayList<>();
    CoinAdapter adapter;
    RecyclerView recyclerView;
    OkHttpClient client;
    Request request;
    SwipeRefreshLayout swipeRefreshLayout;

    String API_KEY = "d652d94e-50d3-4c0e-8c2c-414e3c8989ed";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coin_layout);

        swipeRefreshLayout = findViewById(R.id.rootLayout);
        swipeRefreshLayout.post(() -> loadFirst10Coin());

        swipeRefreshLayout.setOnRefreshListener(() -> {
            items.clear();
            loadFirst10Coin();
            setupAdapter();
        });

        recyclerView = findViewById(R.id.coinList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        setupAdapter();

    }
     private void setupAdapter() {
        adapter = new CoinAdapter(recyclerView, MainActivity.this,items);
        recyclerView.setAdapter(adapter);
        adapter.setiLoadMore(() -> {
            if (items.size() <= 500) {
                loadNext10Coin();
            } else {
                Toast.makeText(MainActivity.this, "Max items is 500", Toast.LENGTH_SHORT).show();
            }
        });
     }

     private void loadNext10Coin() {
        client = new OkHttpClient();
        request = new Request.Builder().url(String.format("https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest"))
                .build();
        swipeRefreshLayout.setRefreshing(true);
        client.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                        String body = response.body().string();
                        Gson gson = new Gson();
                        List<CoinModel> newItems = gson.fromJson(body, new TypeToken<List<CoinModel>>(){}.getType());

                        runOnUiThread(() -> {
                            items.addAll(newItems);
                            adapter.setLoaded();
                            adapter.updateData(items);
                            swipeRefreshLayout.setRefreshing(false);
                        });

                    }
                });
     }

     private void loadFirst10Coin() {
         client = new OkHttpClient();
         request = new Request.Builder().url(String.format("https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest?CMC_PRO_API_KEY=d652d94e-50d3-4c0e-8c2c-414e3c8989ed"))
                 .build();
         swipeRefreshLayout.setRefreshing(true);
         client.newCall(request)
                 .enqueue(new Callback() {
                     @Override
                     public void onFailure(Call call, IOException e) {
                         Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                     }

                     @Override
                     public void onResponse(Call call, Response response) throws IOException {

                         String body = response.body().string();
                         Gson gson = new Gson();
                         List<CoinModel> newItems = gson.fromJson(body, new TypeToken<List<CoinModel>>(){}.getType());

                         runOnUiThread(() -> adapter.updateData(newItems));

                     }
                 });
         if (swipeRefreshLayout.isRefreshing()) {
             swipeRefreshLayout.setRefreshing(false);
         }
     }

}