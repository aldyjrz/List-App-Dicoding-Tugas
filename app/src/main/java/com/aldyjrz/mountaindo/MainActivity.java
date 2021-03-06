package com.aldyjrz.mountaindo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Intent;
 import android.os.Bundle;
 import android.util.Log;
import android.view.Menu;
 import android.view.MenuItem;
import android.widget.Toast;

import com.aldyjrz.mountaindo.Adapter.NewsAdapter;
import com.aldyjrz.mountaindo.Adapter.NewsModels;
 import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
 import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {


    private static final String TAG = "MainActivity";
    ProgressDialog pDialog;
    BaseApp app;
    private ArrayList<NewsModels> list = new ArrayList<>();
    RecyclerView recyclerView;
    SwipeRefreshLayout mswipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        app = BaseApp.obtainApp(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Berita Kita");
        }

        //deklarasi progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Tunggu Sebentar...");
        //loading tidak bisa dibatalkan
        pDialog.setCancelable(false);

        mswipeRefreshLayout = findViewById(R.id.swipeRefresh);
        recyclerView = findViewById(R.id.recycleview);
        recyclerView.setHasFixedSize(true);

        //panggil fungsi untuk getdata dari API newsapi.org
        getData();
        mswipeRefreshLayout.setOnRefreshListener(this);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.about:
                startActivity(new Intent(getApplicationContext(), AboutActivity.class));
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void showpDialog() {
        if (!pDialog.isShowing()) {
            pDialog.show();
        }
    }



    private void hidepDialog() {
        if (pDialog.isShowing()) {
            pDialog.dismiss();
        }
    }
    private void showRecyclerCardView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        NewsAdapter cardViewHeroAdapter = new NewsAdapter(list);
        recyclerView.setAdapter(cardViewHeroAdapter);

    }


    public void getData() {
        showpDialog();


        //GET Data dari API menggunakan Library Volley
        String urlJsonArry = "http://newsapi.org/v2/top-headlines?country=id&apiKey=d31dc723e49e4caf8fedda7b8637c92c";
        JsonObjectRequest jsonObjRequest = new JsonObjectRequest(Request.Method.GET,
                urlJsonArry, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "Get Data News: " + response);

                try {
                    JSONArray data = response.getJSONArray("articles");


                    for(int i = 0; i < data.length(); i++) {

                        JSONObject jsonObject = (JSONObject) data.get(i);
                        String nama = jsonObject.getJSONObject("source").getString("name");
                        String author = jsonObject.getString("author");

                        String judul = jsonObject.getString("title");
                        String deskripsi = jsonObject.getString("description");
                        String image = jsonObject.getString("urlToImage");
                        String publish = jsonObject.getString("publishedAt");
                        String link = jsonObject.getString("url");
                        String content = jsonObject.getString("content");

                        NewsModels newsModel = new NewsModels();
                        newsModel.setSourceName(nama);
                        newsModel.setAuthor(author);
                        newsModel.setLink(link);

                        newsModel.setTitle(judul);
                        newsModel.setDescription(deskripsi);
                        newsModel.setImage(image);
                        newsModel.setPublishDate(publish);
                        newsModel.setContent(content);
                        Log.d("BERITA", newsModel.getTitle());

                        list.add(newsModel);
                        showRecyclerCardView();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Server Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }

                hidepDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("ERROR", error.toString());
                hidepDialog();


            }
        }) {
        };
        // Adding request to request queue
        BaseApp.getInstance().addToRequestQueue(jsonObjRequest);
    }

    @Override
    public void onRefresh() {
        getData();
        mswipeRefreshLayout.setRefreshing(false);
    }
}
