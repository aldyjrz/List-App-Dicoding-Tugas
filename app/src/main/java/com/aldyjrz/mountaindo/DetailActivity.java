package com.aldyjrz.mountaindo;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aldyjrz.mountaindo.Adapter.NewsAdapter;
import com.aldyjrz.mountaindo.Adapter.NewsModels;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.request.RequestOptions;

public class DetailActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    ProgressDialog pDialog;
     String photo, authorName, date, title, content;
    TextView tvTanggal, tvAuthor, tvKonten, tvTitle;
    ImageView imgPhoto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_detail);

        initialRes();
        if(!photo.isEmpty()){

            //Mengubah title menjadi judul berita
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(title);
            }
            hidepDialog();

            //memasukan text ke textview dari data yang didapatkan dari setData()
            tvTanggal.setText(date);
            tvAuthor.setText(authorName);
            tvKonten.setText(content);
            tvTitle.setText(title);

            //menampilkan gambar dari url string yang didapat
            Glide.with(getApplicationContext())
                    .load(photo)
                    .apply(new RequestOptions().override(150, 220))
                    .into(imgPhoto);

        }



    }
    void initialRes(){
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Tunggu Sebentar...");
        pDialog.setCancelable(false);
        tvTanggal = findViewById(R.id.tv_date);
        tvAuthor = findViewById(R.id.tv_author);
        tvKonten = findViewById(R.id.tv_news_detail2);
        tvTitle = findViewById(R.id.tv_news_title2);
        imgPhoto = findViewById(R.id.news_img);
        showpDialog();

    }

    //method untuk load data yang dipanggil dari adapter
    public void setData(String foto, String nama, String tanggal, String judul, String konten){
        this.photo = foto;
        this.authorName = nama;
        this.date = tanggal;
        this.title = judul;
        this.content = konten;
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
}
