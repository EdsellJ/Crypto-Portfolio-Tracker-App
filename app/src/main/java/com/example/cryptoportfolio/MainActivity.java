package com.example.cryptoportfolio;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
public class MainActivity extends AppCompatActivity {

    private static String JSON_URL = "https://run.mocky.io/v3/277b2862-0457-4ee7-9ad0-bc1f61fe7fd6";

    List<CoinModel> coinList;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        // Instantiate coin list and recyclerview to hold json elements
        coinList = new ArrayList<>();
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);

        GetData getData = new GetData();
        getData.execute();
    }

    public class GetData extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            String current = "";

            try {
                URL url;
                HttpURLConnection urlConnection = null;

                // Try connection to json url
                try {
                    url = new URL(JSON_URL);
                    urlConnection = (HttpURLConnection) url.openConnection();

                    InputStream is = urlConnection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);

                    // Read input stream
                    int data = isr.read();
                    while(data != -1) {
                        current += (char) data;
                        data = isr.read();
                    }

                    return current;

                    // Catch exceptions
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if(urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return current;
        }

        @Override
        protected void onPostExecute(String s) {

            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("Coins"); // Pulls json array name

                for (int i = 0; i < jsonArray.length(); i++) {  // iterate through json array
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                    CoinModel model = new CoinModel();
                    model.setName(jsonObject1.getString("name"));   // Set variables from json ids
                    model.setPrice(jsonObject1.getString("price"));
                    model.setImg(jsonObject1.getString("image"));

                    coinList.add(model);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            PutDataIntoRecyclerView(coinList);
        }
    }

    private void PutDataIntoRecyclerView(List<CoinModel> coinList) {

        // Create coin adapter
        CoinAdapter coinAdapter = new CoinAdapter(this, coinList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));   // New linear layout for parsed json
        recyclerView.setAdapter(coinAdapter);
    }

}
