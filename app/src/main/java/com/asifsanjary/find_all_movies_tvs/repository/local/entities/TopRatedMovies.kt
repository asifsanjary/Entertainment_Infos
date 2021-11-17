package com.asifsanjary.find_all_movies_tvs.repository.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


//TODO: check if FTS4/3 needed for movie_id

@Entity(tableName = "topRatedMovies")
data class TopRatedMovies(
    @ColumnInfo(name = "id") val uid: Int,
    @ColumnInfo(name = "pageNo") val pageNo : Int,
    @PrimaryKey @ColumnInfo(name = "position") val position : Int
)
