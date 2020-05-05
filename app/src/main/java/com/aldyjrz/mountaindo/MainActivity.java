package com.aldyjrz.mountaindo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.aldyjrz.mountaindo.Adapter.NewsModel;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    ProgressDialog pDialog;
    BaseApp app;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_news);
        app = BaseApp.obtainApp(this);
         pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
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
        rvHeroes.layoutManager = LinearLayoutManager(this)
        val cardViewHeroAdapter = CardViewHeroAdapter(list)
        rvHeroes.adapter = cardViewHeroAdapter
    }
    public void getData() {
        showpDialog();
        final SharedPreferences prefs = getSharedPreferences("BSH", 0);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putBoolean("find_driver", true);
        edit.apply();
         String urlJsonArry = "http://newsapi.org/v2/top-headlines?country=id&apiKey=d31dc723e49e4caf8fedda7b8637c92c";
        JsonObjectRequest jsonObjRequest = new JsonObjectRequest(Request.Method.GET,
                urlJsonArry, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "Get Goresto Coordinate: " + response);

                try {
                    JSONArray data = response.getJSONObject("data").getJSONArray("cards");


                    for(int i = 0; i < data.length(); i++) {


                        JSONObject jsonObject = (JSONObject) data.get(i);
                        String nama = jsonObject.getJSONObject("source").getString("name");
                        String author = jsonObject.getString("author");

                        String judul = jsonObject.getString("title");
                        String deskripsi = jsonObject.getString("description");
                        String image = jsonObject.getString("urlToImage");
                        String publish = jsonObject.getString("publishedAt");
                        String content = jsonObject.getString("content");


                        NewsModel newsModel = new NewsModel();
                        newsModel.setSourceName(nama);
                        newsModel.setAuthor(author);
                        newsModel.setTitle(judul);
                        newsModel.setDescription(deskripsi);
                        newsModel.setImage(image);
                        newsModel.setPublishDate(publish);
                        newsModel.setContent(content);




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
                VolleyLog.d("ERROR", "Can't Connect to gojek Server");
                hidepDialog();


            }
        }) {


        };

        // Adding request to request queue
        BaseApp.getInstance().addToRequestQueue(jsonObjRequest);
    }

}
