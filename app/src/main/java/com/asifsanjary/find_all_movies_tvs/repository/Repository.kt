package com.asifsanjary.find_all_movies_tvs.repository

import android.content.Context
import android.util.Log
import com.asifsanjary.find_all_movies_tvs.repository.local.LocalRepository
import com.asifsanjary.find_all_movies_tvs.repository.local.entities.MovieBasic
import com.asifsanjary.find_all_movies_tvs.repository.local.MoviePositionInCategory
import com.asifsanjary.find_all_movies_tvs.repository.network.NetworkRepository
import com.asifsanjary.find_all_movies_tvs.repository.network.model.MovieItem
import com.asifsanjary.find_all_movies_tvs.view.OnResponseVm
import com.asifsanjary.find_all_movies_tvs.view.utils.GenreUtil.Companion.getGenresString
import com.asifsanjary.find_all_movies_tvs.view.utils.MovieCategoryInteger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Repository(private val localRepository: LocalRepository, private val networkRepository: NetworkRepository){

    private val className : String = "Repository"

    suspend fun getMoviePreviewDataList(context: Context, movieCategoryInteger: MovieCategoryInteger, pageNo: Int, response: OnResponseVm) = withContext(Dispatchers.IO){
        val movieList = localRepository.getMoviePreviewDataList(movieCategoryInteger, pageNo)

        if (movieList.isNotEmpty()) {
            response.onReceived(movieList)
            Log.d(className, "Quantity[${movieList.size}] $movieCategoryInteger movies received from Local")
        }
        else {
            Log.d(className, "No $movieCategoryInteger movies received from local")

            networkRepository.getMoviesList(pageNo, movieCategoryInteger, object : OnResponse {
                override fun onReceived(movies: List<MovieItem>) {
                    if (movies.isNotEmpty()) {
                        Log.d(className, "Quantity[${movies.size}] $movieCategoryInteger movies received from Server")
                        val movieBasicList: List<MovieBasic> = convertToMoviePreviewList(movies)

                        val listOfMoviePositionInCategory: List<MoviePositionInCategory> =
                            convertToListOfMoviePositionInCategory(movies, pageNo)

                        // TODO: need to learn more
                        GlobalScope.launch {
                            insertMovies(context, movieBasicList)
                            insertListOfMoviePositionInCategory(context, movieCategoryInteger, listOfMoviePositionInCategory)
                        }

                        response.onReceived(movieBasicList)
                    }
                    else {
                        Log.d(className, "$movieCategoryInteger List Size 0 or null")
                    }
                }

                override fun onError(errorMsg: String) {
                    Log.d(className, "$movieCategoryInteger Movies Error")
                }

            })
        }
    }

    private fun convertToListOfMoviePositionInCategory(movieItemList: List<MovieItem>, pageNo: Int): List<MoviePositionInCategory> {
        val newListOfMoviePositionInCategory: MutableList<MoviePositionInCategory> = mutableListOf()

        val positionOffset = pageNo * 20

        for (i in movieItemList.indices) {
            val moviePositionInCategory = MoviePositionInCategory(
                movieItemList[i].id,
                pageNo,
                positionOffset + i
            )
            newListOfMoviePositionInCategory.add(moviePositionInCategory)
        }

        return newListOfMoviePositionInCategory.toList()
    }

    private fun convertToMoviePreviewList(movieItemList: List<MovieItem>): List<MovieBasic> {
        val newMoviesList: MutableList<MovieBasic> = mutableListOf()

        for (movieItem in movieItemList) {
            val movieDetails = MovieBasic(
                movieItem.id,
                movieItem.adult,
                movieItem.backdropPath,
                getGenresString(movieItem.genreIds),
                movieItem.originalLanguage,
                movieItem.originalTitle,
                movieItem.overview,
                movieItem.popularity,
                movieItem.posterPath,
                movieItem.releaseDate,
                movieItem.title,
                movieItem.video,
                movieItem.voteCount,
                movieItem.voteAverage
            )
            newMoviesList.add(movieDetails)
        }

        return newMoviesList.toList()
    }

    suspend fun insertMovies(context: Context, movieList: List<MovieBasic>) {
        localRepository.insertMovies(context, movieList)
    }

    suspend fun insertListOfMoviePositionInCategory(context: Context, movieCategoryInteger: MovieCategoryInteger, listOfMoviePositionInCategory: List<MoviePositionInCategory>) {
        localRepository.insertListOfMoviePositionInCategory(context, movieCategoryInteger, listOfMoviePositionInCategory)
    }
}

interface OnResponse {
    fun onReceived(movies : List<MovieItem>)
    fun onError(errorMsg: String)
}