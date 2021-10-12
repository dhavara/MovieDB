package com.example.moviedb.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.moviedb.R;

public class MovieDetailsActivity extends AppCompatActivity {

    private TextView lbl_text;
    private String movie_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Intent intent = getIntent();
        movie_id = intent.getStringExtra("movie_id");

        lbl_text = findViewById(R.id.lbl_movie_details);
        lbl_text.setText(movie_id);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}