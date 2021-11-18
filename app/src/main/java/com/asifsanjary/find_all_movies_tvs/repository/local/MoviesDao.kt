package com.asifsanjary.find_all_movies_tvs.repository.local

import androidx.room.*
import com.asifsanjary.find_all_movies_tvs.repository.local.entities.*
import com.asifsanjary.find_all_movies_tvs.repository.local.entities.DbUpdateTime

@Dao
interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieBasic>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDbUpdateTime(dbUpdateTime: DbUpdateTime)

    @Insert
    suspend fun insertTopRatedMovies(topRatedMoviesList: List<TopRatedMovies>)

    @Insert
    suspend fun insertNowPlayingMovies(nowPlayingMoviesList: List<NowPlayingMovies>)

    @Insert
    suspend fun insertPopularMovies(popularMoviesList: List<PopularMovies>)

    @Insert
    suspend fun insertUpcomingMovies(upcomingMoviesList: List<UpcomingMovies>)

    @Update
    suspend fun updateMovies(movies: MovieBasic)

    @Query("DELETE FROM movies")
    suspend fun deleteAllMovies()

    @Query("DELETE FROM topRatedMovies")
    suspend fun deleteAllTopRatedMovies()

    @Query("DELETE FROM popularMovies")
    suspend fun deleteAllPopularMovies()

    @Query("DELETE FROM nowPlayingMovies")
    suspend fun deleteAllNowPlayingMovies()

    @Query("DELETE FROM upcomingMovies")
    suspend fun deleteAllUpcomingMovies()

    @Query("SELECT * FROM movies")
    suspend fun getMovieDetails(): List<MovieBasic>

    @Query("SELECT dbUpdateTime.timestamp FROM dbUpdateTime WHERE dbUpdateTime.id = :category")
    suspend fun getDbUpdateTime(category: Int): Long?

    @Query("SELECT movies.id, movies.adult, movies.backdrop_path, movies.genres_list, movies.original_language, movies.original_title, movies.overview, movies.popularity, movies.poster_path, movies.release_date, movies.title, movies.video, movies.vote_average, movies.vote_count FROM movies INNER JOIN topRatedMovies ON movies.id = topRatedMovies.id WHERE topRatedMovies.pageNo = :pageNo ORDER BY topRatedMovies.position ASC")
    suspend fun getTopRatedMovies(pageNo: Int): List<MovieBasic>

    @Query("SELECT movies.id, movies.adult, movies.backdrop_path, movies.genres_list, movies.original_language, movies.original_title, movies.overview, movies.popularity, movies.poster_path, movies.release_date, movies.title, movies.video, movies.vote_average, movies.vote_count FROM movies INNER JOIN popularMovies ON movies.id = popularMovies.id WHERE popularMovies.pageNo = :pageNo ORDER BY popularMovies.position ASC")
    suspend fun getPopularMovies(pageNo: Int): List<MovieBasic>

    @Query("SELECT movies.id, movies.adult, movies.backdrop_path, movies.genres_list, movies.original_language, movies.original_title, movies.overview, movies.popularity, movies.poster_path, movies.release_date, movies.title, movies.video, movies.vote_average, movies.vote_count FROM movies INNER JOIN nowPlayingMovies ON movies.id = nowPlayingMovies.id WHERE nowPlayingMovies.pageNo = :pageNo ORDER BY nowPlayingMovies.position ASC")
    suspend fun getNowPlayingMovies(pageNo: Int): List<MovieBasic>

    @Query("SELECT movies.id, movies.adult, movies.backdrop_path, movies.genres_list, movies.original_language, movies.original_title, movies.overview, movies.popularity, movies.poster_path, movies.release_date, movies.title, movies.video, movies.vote_average, movies.vote_count FROM movies INNER JOIN upcomingMovies ON movies.id = upcomingMovies.id WHERE upcomingMovies.pageNo = :pageNo ORDER BY upcomingMovies.position ASC")
    suspend fun getUpcomingMovies(pageNo: Int): List<MovieBasic>
}