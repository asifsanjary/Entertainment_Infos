package com.asifsanjary.find_all_movies_tvs.repository.local

import android.content.Context
import com.asifsanjary.find_all_movies_tvs.repository.local.entities.*
import com.asifsanjary.find_all_movies_tvs.view.utils.MovieCategoryInteger

// TODO use proper singleton
class LocalRepository(private val moviesDao : MoviesDao){
    suspend fun getMoviePreviewDataList(movieCategoryInteger: MovieCategoryInteger, pageNo: Int): List<MovieBasic>{

        return when (movieCategoryInteger) {
            MovieCategoryInteger.UPCOMING -> moviesDao.getUpcomingMovies(pageNo)
            MovieCategoryInteger.POPULAR -> moviesDao.getPopularMovies(pageNo)
            MovieCategoryInteger.NOW_PLAYING -> moviesDao.getNowPlayingMovies(pageNo)
            else -> {
                moviesDao.getTopRatedMovies(pageNo)
            }
        }
    }

    suspend fun insertMovies(context: Context, movieList: List<MovieBasic>) {
        AppDatabase.getDatabase(context).moviesDao().insertMovies(movieList)
    }

    suspend fun insertListOfMoviePositionInCategory(context: Context, movieCategoryInteger: MovieCategoryInteger, movieList: List<MoviePositionInCategory>) {
        val moviesDao = AppDatabase.getDatabase(context).moviesDao()
        when (movieCategoryInteger) {
            MovieCategoryInteger.UPCOMING -> moviesDao.insertUpcomingMovies(movieList.map { moviePositionInCategory ->
                UpcomingMovies( moviePositionInCategory.uid, moviePositionInCategory.pageNo, moviePositionInCategory.position)
            })

            MovieCategoryInteger.POPULAR -> moviesDao.insertPopularMovies(movieList.map { moviePositionInCategory ->
                PopularMovies( moviePositionInCategory.uid, moviePositionInCategory.pageNo, moviePositionInCategory.position)
            })

            MovieCategoryInteger.NOW_PLAYING -> moviesDao.insertNowPlayingMovies(movieList.map { moviePositionInCategory ->
                NowPlayingMovies( moviePositionInCategory.uid, moviePositionInCategory.pageNo, moviePositionInCategory.position)
            })

            MovieCategoryInteger.TOP_RATED ->  moviesDao.insertTopRatedMovies(movieList.map { moviePositionInCategory ->
                TopRatedMovies( moviePositionInCategory.uid, moviePositionInCategory.pageNo, moviePositionInCategory.position)
            })
        }
    }
}