package com.asifsanjary.find_all_movies_tvs.view.utils

class GenreUtil {
    companion object {

        const val NO_GENRE_FOUND : String = ""

        fun getMovieGenreString(genreId: Int) : String{
            var returnString = ""
            when (genreId) {
                28 -> returnString = "Action"
                12 -> returnString = "Adventure"
                16 -> returnString = "Animation"
                35 -> returnString = "Comedy"
                80 -> returnString = "Crime"
                99 -> returnString = "Documentary"
                18 -> returnString = "Drama"
                10751 -> returnString = "Family"
                14 -> returnString =  "Fantasy"
                36 -> returnString = "History"
                27 -> returnString = "Horror"
                10402 -> returnString = "Music"
                9648 -> returnString = "Mystery"
                10749 -> returnString = "Romance"
                878 -> returnString = "Science Fiction"
                10770 -> returnString = "TV Movie"
                53 -> returnString = "Thriller"
                10752 -> returnString = "War"
                37 -> returnString = "Western"
            }
            return returnString
        }

        fun getGenresString(genreList : List<Int>?) : String {
            if(genreList != null && genreList.isNotEmpty()) {
                var genresString: String = getMovieGenreString(genreList[0])
                for (i in 1 until genreList.size) {
                    genresString = genresString.plus(" - ").plus(getMovieGenreString(genreList[i]))
                }
                return genresString
            }
            return NO_GENRE_FOUND
        }
    }
}