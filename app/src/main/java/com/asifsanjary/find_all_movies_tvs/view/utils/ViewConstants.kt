package com.asifsanjary.find_all_movies_tvs.view.utils

import com.asifsanjary.find_all_movies_tvs.repository.local.entities.MovieBasic

object ViewConstants {
    const val MAIN_ACTIVITY_MESSAGE_KEY : String = "com.asifsanjary.find_all_movies_tv.main_activity.MESSAGE"
    const val PAGE_START : Int = 1
    const val PAGE_LIMIT : Int = 25
    val DUMMY_MOVIE_DATA : MovieBasic = MovieBasic(-1,
        false,
        null,
        "",
        "",
        "",
        null,
        0.0,
        null,
        "",
        "",
        false,
        0,
        0.0)
}