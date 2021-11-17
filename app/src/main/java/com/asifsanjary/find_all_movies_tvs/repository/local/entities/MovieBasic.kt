package com.asifsanjary.find_all_movies_tvs.repository.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


//TODO: check if FTS needed for movie_id

@Entity(tableName = "movies")
data class MovieBasic(
    @PrimaryKey @ColumnInfo(name = "id") val uid: Int,
    @ColumnInfo(name = "adult") val adult : Boolean = false,
    @ColumnInfo(name = "backdrop_path") val backdropPath : String?,
    @ColumnInfo(name = "genres_list") val genres : String,
//    @ColumnInfo(name = "budget") val budget : Int,
//    @ColumnInfo(name = "imdb_id") val imdbId : String?,
    @ColumnInfo(name = "original_language") val originalLanguage : String,
    @ColumnInfo(name = "original_title") val originalTitle : String,
    @ColumnInfo(name = "overview") val overview : String?,
    @ColumnInfo(name = "popularity") val popularity : Double,
    @ColumnInfo(name = "poster_path") val posterPath : String?,
    @ColumnInfo(name = "release_date") val releaseDate : String,
//    @ColumnInfo(name = "revenue") val revenue : Int,
//    @ColumnInfo(name = "runtime") val runtime : Int?,
//    @ColumnInfo(name = "tagline") val tagline : String?,
    @ColumnInfo(name = "title") val title : String,
    @ColumnInfo(name = "video") val video : Boolean,
    @ColumnInfo(name = "vote_count") val voteCount : Int,
    @ColumnInfo(name = "vote_average") val voteAverage : Double,
)
