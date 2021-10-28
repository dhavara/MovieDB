package com.example.moviedb.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.moviedb.R;
import com.example.moviedb.helper.Const;
import com.example.moviedb.model.Movies;
import com.example.moviedb.viewmodel.MovieViewModel;
import com.google.android.material.snackbar.Snackbar;


public class MovieDetailsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MovieDetailsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static MovieDetailsFragment newInstance(String param1, String param2) {
        MovieDetailsFragment fragment = new MovieDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    private TextView lbl_movie_movieTitle, lbl_movie_movieRating, lbl_movie_movieGenre, lbl_movie_movieDesc, lbl_movie_movieRelDate, lbl_movie_movieTagline, lbl_movies_popularity;
    private String movie_id = "", movieGenre = "";
    private MovieViewModel viewModel;
    private ImageView img_poster, img_movie_movieBackdrop;
    private LinearLayout ll_prodComp;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_movie_details, container, false);

        movie_id = getArguments().getString("movieId");

        img_poster = view.findViewById(R.id.img_poster_movieDetails);
        img_movie_movieBackdrop = view.findViewById(R.id.img_movie_movieBackdrop);
        lbl_movie_movieTitle = view.findViewById(R.id.lbl_movie_movieTitle);
        lbl_movie_movieTagline = view.findViewById(R.id.lbl_movie_movieTagline);
        lbl_movies_popularity = view.findViewById(R.id.lbl_movies_popularity);
        lbl_movie_movieDesc = view.findViewById(R.id.lbl_movie_movieDesc);
        lbl_movie_movieGenre = view.findViewById(R.id.lbl_movie_movieGenre);
        lbl_movie_movieRating = view.findViewById(R.id.lbl_movie_movieRating);
        lbl_movie_movieRelDate = view.findViewById(R.id.lbl_movie_movieRelDate);
        ll_prodComp = view.findViewById(R.id.ll_movie_prodComp);

        viewModel = new ViewModelProvider(getActivity()).get(MovieViewModel.class);
        viewModel.getMovieById(movie_id);
        viewModel.getResultGetMovieById().observe(getActivity(), showResultMovie);

        return view;
    }
    private final Observer<Movies> showResultMovie = new Observer<Movies>() {
        @Override
        public void onChanged(Movies movies) {
            String img_backdrop = Const.IMG_URL + movies.getBackdrop_path();
            String img_path = Const.IMG_URL + movies.getPoster_path().toString();
            Glide.with(MovieDetailsFragment.this).load(img_path).into(img_poster);
            Glide.with(MovieDetailsFragment.this).load(img_backdrop).into(img_movie_movieBackdrop);
            for (int i = 0; i < movies.getGenres().size(); i++) {
                if(i == movies.getGenres().size()-1){
                    movieGenre+=movies.getGenres().get(i).getName();
                }else{
                    movieGenre+=movies.getGenres().get(i).getName()+", ";
                }
            }

            for (int i = 0; i < movies.getProduction_companies().size(); i++) {
                ImageView img_movie_prodComp = new ImageView(ll_prodComp.getContext());
                String prodLogo = Const.IMG_URL + movies.getProduction_companies().get(i).getLogo_path();
                String prodName = movies.getProduction_companies().get(i).getName();
                if (movies.getProduction_companies().get(i).getLogo_path() == null) {
                    img_movie_prodComp.setImageDrawable(getResources().getDrawable(R.drawable.production_company, getActivity().getTheme()));
                }else if (prodLogo != "https://image.tmdb.org/3/t/p/w500/null") {
                    Glide.with(getActivity()) .load(prodLogo).into(img_movie_prodComp);
                }
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(250, 250);
                lp.setMargins(20,0,20,0);
                img_movie_prodComp.setLayoutParams(lp);
                ll_prodComp.addView(img_movie_prodComp);
                img_movie_prodComp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar snackbar = Snackbar.make(view, prodName, Snackbar.LENGTH_SHORT);
                        snackbar.setAnchorView(R.id.bottom_nav_main_menu);
                        snackbar.show();
                    }
                });
            }
            lbl_movie_movieTitle.setText(movies.getTitle());
            lbl_movie_movieTagline.setText(movies.getTagline());
            lbl_movies_popularity.setText("" + movies.getPopularity());
            lbl_movie_movieDesc.setText(movies.getOverview());
            lbl_movie_movieGenre.setText(movieGenre);
            lbl_movie_movieRating.setText("" + movies.getVote_average() + " (" + movies.getVote_count() +") ");
            lbl_movie_movieRelDate.setText(movies.getRelease_date());

        }
    };
}