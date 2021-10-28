package com.example.moviedb.retrofit;

import com.example.moviedb.model.Movies;
import com.example.moviedb.model.NowPlaying;
import com.example.moviedb.model.UpComing;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiEndPoint {

    @GET("movie/{movie_id}")
    Call<Movies> getMovieById(
            @Path("movie_id") String movieId,
            @Query("api_key") String apiKey
    );

    @GET("movie/now_playing")
    Call<NowPlaying> getNowPlaying(
            @Query("api_key") String apiKey
    );

    @GET("movie/upcoming")
    Call<UpComing> getUpComing(
            @Query("api_key") String apiKey
    );

}
