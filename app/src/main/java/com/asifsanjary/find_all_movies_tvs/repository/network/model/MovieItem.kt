package com.asifsanjary.find_all_movies_tvs.repository.network.model

import com.google.gson.annotations.SerializedName

open class MovieItem{
    @SerializedName("poster_path")
    var posterPath : String = ""
    @SerializedName("adult")
    var adult : Boolean = false
    @SerializedName("overview")
    var overview : String = ""
    @SerializedName("release_date")
    var releaseDate : String = ""
    @SerializedName("genre_ids")
    var genreIds : List<Int>? = null
    @SerializedName("id")
    var id : Int = -1
    @SerializedName("original_title")
    var originalTitle : String = ""
    @SerializedName("original_language")
    var originalLanguage : String = ""
    @SerializedName("title")
    var title : String = ""
    @SerializedName("backdrop_path")
    var backdropPath : String = ""
    @SerializedName("popularity")
    var popularity : Double = 0.0
    @SerializedName("vote_count")
    var voteCount : Int = 0
    @SerializedName("vote_average")
    var voteAverage : Double = 0.0
    @SerializedName("video")
    var video : Boolean = false
}
