package com.asifsanjary.find_all_movies_tvs.repository.network.model

import com.google.gson.annotations.SerializedName

class MovieItemList : PageItem(){

    @SerializedName("results")
    var results : List<MovieItem>? = null
}