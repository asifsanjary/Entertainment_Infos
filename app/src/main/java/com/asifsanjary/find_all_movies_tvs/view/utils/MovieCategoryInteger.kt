package com.asifsanjary.find_all_movies_tvs.view.utils

enum class MovieCategoryInteger(val category: Int) {
    POPULAR(1001),
    NOW_PLAYING(1002),
    UPCOMING(1003),
    TOP_RATED(1004)
}

enum class MovieCategoryString(val category: String) {
    POPULAR("Popular"),
    NOW_PLAYING("Now Playing"),
    UPCOMING("Upcoming"),
    TOP_RATED("Top Rated")
}