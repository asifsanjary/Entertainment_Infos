package com.asifsanjary.find_all_movies_tvs.view.utils

enum class MovieCategoryInteger(val category: Int) {
    POPULAR(0),
    NOW_PLAYING(1),
    UPCOMING(2),
    TOP_RATED(3)
}

enum class MovieCategoryString(val category: String) {
    POPULAR("Popular"),
    NOW_PLAYING("Now Playing"),
    UPCOMING("Upcoming"),
    TOP_RATED("Top Rated")
}