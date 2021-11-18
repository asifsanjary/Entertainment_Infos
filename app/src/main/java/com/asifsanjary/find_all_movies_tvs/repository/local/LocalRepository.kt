package com.asifsanjary.find_all_movies_tvs.repository.local

import android.util.Log
import com.asifsanjary.find_all_movies_tvs.repository.local.entities.*
import com.asifsanjary.find_all_movies_tvs.repository.network.Constant.ONE_DAY_MILLISECONDS
import com.asifsanjary.find_all_movies_tvs.repository.local.entities.DbUpdateTime
import com.asifsanjary.find_all_movies_tvs.view.utils.MovieCategoryInteger

// TODO use proper singleton
class LocalRepository(private val moviesDao : MoviesDao){

    private val className = "LocalRepository"

    suspend fun getMoviePreviewDataList(movieCategoryInteger: MovieCategoryInteger, pageNo: Int): List<MovieBasic>{
        if(pageNo == 1) {
            checkLastDbUpdateTime(movieCategoryInteger)
        }

        return when (movieCategoryInteger) {
            MovieCategoryInteger.UPCOMING -> moviesDao.getUpcomingMovies(pageNo)
            MovieCategoryInteger.POPULAR -> moviesDao.getPopularMovies(pageNo)
            MovieCategoryInteger.NOW_PLAYING -> moviesDao.getNowPlayingMovies(pageNo)
            else -> {
                moviesDao.getTopRatedMovies(pageNo)
            }
        }
    }

    private suspend fun checkLastDbUpdateTime(category: MovieCategoryInteger) {

        val lastUpdateTime = moviesDao.getDbUpdateTime(category.category)
        if (lastUpdateTime == null) {
            Log.d(className, "lastUpdateTime for $category from DB is null")
            return
        }

        if(System.currentTimeMillis() - lastUpdateTime >= ONE_DAY_MILLISECONDS) {
            when (category) {
                MovieCategoryInteger.POPULAR -> moviesDao.deleteAllPopularMovies ()
                MovieCategoryInteger.NOW_PLAYING -> moviesDao.deleteAllNowPlayingMovies()
                MovieCategoryInteger.TOP_RATED -> moviesDao.deleteAllTopRatedMovies ()
                MovieCategoryInteger.UPCOMING -> moviesDao.deleteAllUpcomingMovies ()
            }
            Log.d(className, "Deleted $category from DB")
        }
        else{
            Log.d(className, "Update time of $category is less than 1 day")
        }
    }

    suspend fun insertMovies(movieList: List<MovieBasic>) {
        moviesDao.insertMovies(movieList)
    }

    suspend fun insertListOfMoviePositionInCategory(
        movieCategoryInteger: MovieCategoryInteger,
        movieList: List<MoviePositionInCategory>
    ) {
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

    suspend fun insertDbUpdateTime(category: MovieCategoryInteger) {
        Log.d(className, "Inserting db update time for $category")
        val dbUpdateTime = DbUpdateTime ( category.category, System.currentTimeMillis())
        moviesDao.insertDbUpdateTime(dbUpdateTime)
    }
}