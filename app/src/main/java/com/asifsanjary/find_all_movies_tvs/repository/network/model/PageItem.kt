package com.asifsanjary.find_all_movies_tvs.repository.network.model

import com.google.gson.annotations.SerializedName

open class PageItem {
    @SerializedName("page")
    var page : Int = 0
    @SerializedName("total_pages")
    var totalPages : Int = 0
    @SerializedName("total_results")
    var totalResults : Int = 0
    @SerializedName("dates")
    var dates : Dates? = null

    class Dates {
        @SerializedName("maximum")
        var maximum : String? = null
        @SerializedName("minimum")
        var minimum : String? = null
    }
}