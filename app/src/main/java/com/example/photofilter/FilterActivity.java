package com.example.photofilter;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class FilterActivity extends AppCompatActivity {

    ImageView imageView;
    public BitmapDrawable ogBmp;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        imageView = findViewById(R.id.displayPhoto);

        String photo = getIntent().getStringExtra("photoUri");
        Uri uri = Uri.parse(photo);

        Glide.with(this)
                .load(uri)
                .placeholder(R.drawable.ic_launcher_background)
                .into(imageView);
    }
}