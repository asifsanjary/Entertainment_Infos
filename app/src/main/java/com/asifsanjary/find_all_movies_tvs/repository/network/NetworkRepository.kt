package com.asifsanjary.find_all_movies_tvs.repository.network

import android.util.Log
import com.asifsanjary.find_all_movies_tvs.repository.OnResponse
import com.asifsanjary.find_all_movies_tvs.repository.network.Constant.BASE_URL_V3
import com.asifsanjary.find_all_movies_tvs.repository.network.Constant.DEFAULT_LANGUAGE
import com.asifsanjary.find_all_movies_tvs.repository.network.model.MovieItemList
import com.asifsanjary.find_all_movies_tvs.view.utils.MovieCategoryInteger
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkRepository {
    private val className : String = "NetworkRepository"

    private val tmdbRetrofitV3: Retrofit by lazy {
        Retrofit.Builder().baseUrl(BASE_URL_V3)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val tmdbServiceV3: TMDBService by lazy {
        tmdbRetrofitV3.create(TMDBService::class.java)
    }

    suspend fun getMoviesList(pageNo: Int, movieCategoryInteger: MovieCategoryInteger, listener: OnResponse) {
        val getMovieListCall : Call<MovieItemList> = when (movieCategoryInteger) {
            MovieCategoryInteger.POPULAR -> {
                tmdbServiceV3.getPopularMovies(pageNo, SECRETS.API_KEY, DEFAULT_LANGUAGE)
            }
            MovieCategoryInteger.UPCOMING -> {
                tmdbServiceV3.getUpcomingMovies(pageNo, SECRETS.API_KEY, DEFAULT_LANGUAGE)
            }
            MovieCategoryInteger.NOW_PLAYING -> {
                tmdbServiceV3.getNowPlayingMovies(pageNo, SECRETS.API_KEY, DEFAULT_LANGUAGE)
            }
            else -> {
                tmdbServiceV3.getTopRatedMovies(pageNo, SECRETS.API_KEY, DEFAULT_LANGUAGE)
            }
        }

        getMovieListCall.enqueue(object : Callback<MovieItemList> {
            override fun onResponse(
                call: Call<MovieItemList>,
                response: Response<MovieItemList>
            ) {
                if (response.code() == 200) {
                    val movieItemList : MovieItemList? = response.body()
                    if(movieItemList is MovieItemList) {
                        Log.d(className, "received ${movieItemList.results?.size} $movieCategoryInteger Movies")
                        listener.onReceived(movieItemList.results!!)
                    }
                    else Log.d(className, "could not cast")
                }
            }

            override fun onFailure(call: Call<MovieItemList>, t: Throwable) {
                Log.d(className, "error:\n$t")
                listener.onError("Network Error")
            }
        })
    }
}