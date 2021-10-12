package com.example.moviedb.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.moviedb.R;
import com.example.moviedb.helper.Const;
import com.example.moviedb.model.Movies;
import com.example.moviedb.viewmodel.MovieViewModel;

public class MovieDetailsActivity extends AppCompatActivity {

    private TextView lbl_movie_movieID, lbl_movie_movieTitle, lbl_movie_movieRating, lbl_movie_ratingCount, lbl_movie_movieGenre, lbl_movie_movieDesc;
    private String movie_id = "", movieGenre = "";
    private MovieViewModel viewModel;
    private ImageView img_poster;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        viewModel = new ViewModelProvider(MovieDetailsActivity.this).get(MovieViewModel.class);

        Intent intent = getIntent();
        movie_id = intent.getStringExtra("movie_id");

        img_poster = findViewById(R.id.img_poster_movieDetails);
        lbl_movie_movieID= findViewById(R.id.lbl_movie_movieID);
        lbl_movie_movieTitle = findViewById(R.id.lbl_movie_movieTitle);
        lbl_movie_movieDesc = findViewById(R.id.lbl_movie_movieDesc);
        lbl_movie_movieGenre = findViewById(R.id.lbl_movie_movieGenre);
        lbl_movie_movieRating = findViewById(R.id.lbl_movie_movieRating);
        lbl_movie_ratingCount = findViewById(R.id.lbl_movie_ratingCount);

        viewModel.getMovieById(movie_id);
        viewModel.getResultGetMovieById().observe(MovieDetailsActivity.this, showResultMovie);
    }

    private Observer<Movies> showResultMovie = new Observer<Movies>() {
        @Override
        public void onChanged(Movies movies) {
            String img_path = Const.IMG_URL + movies.getPoster_path().toString();
            Glide.with(MovieDetailsActivity.this).load(img_path).into(img_poster);// nampilin gambar
            for (int i = 0; i < movies.getGenres().size(); i++) {
                if(i == movies.getGenres().size()-1){
                    movieGenre+=movies.getGenres().get(i).getName();
                }else{
                    movieGenre+=movies.getGenres().get(i).getName()+", ";
                }
            }
                lbl_movie_movieID.setText("ID: " + movie_id); //nampilin ID
                lbl_movie_movieTitle.setText(movies.getTitle());
                lbl_movie_movieDesc.setText(movies.getOverview());//nampilin judul
                lbl_movie_movieGenre.setText(movieGenre); //nampilin genre
                lbl_movie_movieRating.setText("Rating: " + "" + movies.getVote_average()); //nampilin rating
                lbl_movie_ratingCount.setText("Rating Count: " + movies.getVote_count()); //nampilin jumlah vote


        }

    };

    @Override
    public void onBackPressed() {
        finish();
    }
}