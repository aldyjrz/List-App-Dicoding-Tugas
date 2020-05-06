package com.aldyjrz.mountaindo;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aldyjrz.mountaindo.Adapter.NewsModels;
import com.bumptech.glide.Glide;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

public class DetailActivity extends AppCompatActivity {
     ProgressDialog pDialog;
     String photo, authorName, date, title, content, link;
    TextView tvTanggal, tvAuthor, tvKonten, tvTitle, tvLink;
    ImageView imgPhoto;
    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_detail);

        initialRes();

        title = getIntent().getStringExtra("judul");

            //Mengubah title menjadi judul berita

        showpDialog();

        String[] getJudul = title.split("- ");
        Log.d("getjudul", getJudul[1]);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getJudul[1]);
        }
        tvLink.setText(getIntent().getStringExtra("link"));
        tvTanggal.setText(getIntent().getStringExtra("tanggal"));
        tvTitle.setText(getIntent().getStringExtra("judul"));

        String konten = getIntent().getStringExtra("konten");
        if(konten.equals("null")){
            konten = "Silahkan klik Gambar Untuk Membaca Berita";
        }

        String nama = getIntent().getStringExtra("nama");
        if(nama.equals("null")){
            nama = getJudul[1];
        }
        tvAuthor.setText(nama);

        tvKonten.setText(konten);
        String gambar = getIntent().getStringExtra("img");


        tvLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getIntent().getStringExtra("link"))));
            }
        });
        Glide.with(getApplicationContext())
                .load(gambar)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                         hidepDialog();

                        Toast.makeText(getApplicationContext(), "Gambar gagal ditampilkan", Toast.LENGTH_SHORT).show();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        hidepDialog();
                        return false;
                    }
                })
                .into(imgPhoto);


        imgPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getIntent().getStringExtra("link"))));
            }
        });


    }



    void initialRes(){
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Tunggu Sebentar...");
        pDialog.setCancelable(false);
        tvTanggal = findViewById(R.id.tv_date);
        tvAuthor = findViewById(R.id.tv_author);
        tvKonten = findViewById(R.id.content);
        tvTitle = findViewById(R.id.tv_title);
        imgPhoto = findViewById(R.id.news_img);
        tvLink = findViewById(R.id.tv_link);
        showpDialog();

    }

    //method untuk load data yang dipanggil dari adapter

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
