package com.asifsanjary.find_all_movies_tvs.repository.network

import com.asifsanjary.find_all_movies_tvs.repository.network.model.MovieItemList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TMDBService {
    @GET("movie/top_rated")
    fun getTopRatedMovies(
        @Query("page") page : Int,
        @Query("api_key") apiKey : String,
        @Query("language") lang: String
    ) : Call<MovieItemList>

    @GET("movie/upcoming")
    fun getUpcomingMovies(
        @Query("page") page : Int,
        @Query("api_key") apiKey : String,
        @Query("language") lang: String
    ) : Call<MovieItemList>

    @GET("movie/popular")
    fun getPopularMovies(
        @Query("page") page : Int,
        @Query("api_key") apiKey : String,
        @Query("language") lang: String
    ) : Call<MovieItemList>

    @GET("movie/now_playing")
    fun getNowPlayingMovies(
        @Query("page") page : Int,
        @Query("api_key") apiKey : String,
        @Query("language") lang: String
    ) : Call<MovieItemList>
}