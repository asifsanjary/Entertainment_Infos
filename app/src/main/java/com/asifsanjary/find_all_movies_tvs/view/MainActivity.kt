package com.asifsanjary.find_all_movies_tvs.view

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.doOnLayout
import androidx.lifecycle.Observer
import com.asifsanjary.find_all_movies_tvs.FindAllMoviesTvApplication
import com.asifsanjary.find_all_movies_tvs.databinding.ActivityMainBinding
import com.asifsanjary.find_all_movies_tvs.repository.local.entities.MovieBasic
import com.asifsanjary.find_all_movies_tvs.view.utils.MovieCategoryInteger
import com.asifsanjary.find_all_movies_tvs.view.utils.MovieCategoryString
import com.asifsanjary.find_all_movies_tvs.view.utils.ViewConstants.MAIN_ACTIVITY_MESSAGE_KEY
import com.asifsanjary.find_all_movies_tvs.view.views.SquareItemView

class MainActivity : AppCompatActivity(), OnClickShowAllItem {

    private val moviePreviewViewModel: MoviePreviewViewModel by viewModels {
        MoviePreviewViewModelFactory((application as FindAllMoviesTvApplication).repository)
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        fetchFromApi()
    }

    private fun initView() {
        binding.popularPreviewHeader.setUp(MovieCategoryString.POPULAR.category, MovieCategoryInteger.POPULAR, this)
        binding.topRatedPreviewHeader.setUp(MovieCategoryString.TOP_RATED.category, MovieCategoryInteger.TOP_RATED, this)
        binding.upcomingPreviewHeader.setUp(MovieCategoryString.UPCOMING.category, MovieCategoryInteger.UPCOMING, this)
        binding.nowPlayingPreviewHeader.setUp(MovieCategoryString.NOW_PLAYING.category, MovieCategoryInteger.NOW_PLAYING, this)

        moviePreviewViewModel.topRatedMoviesList.observe(this, Observer { movies->
            movies?.let{populateView(binding.topRatedMoviePreviewLayout, movies)}
        })

        moviePreviewViewModel.popularMoviesList.observe(this, Observer { movies->
            movies?.let{populateView(binding.popularMoviePreviewLayout, movies)}
        })

        moviePreviewViewModel.upcomingMoviesList.observe(this, Observer { movies->
            movies?.let{populateView(binding.upcomingMoviePreviewLayout, movies)}
        })

        moviePreviewViewModel.nowPlayingMoviesList.observe(this, Observer { movies->
            movies?.let{populateView(binding.nowPlayingMoviePreviewLayout, movies)}
        })
    }

    private fun fetchFromApi() {
        moviePreviewViewModel.getMoviesList(this, MovieCategoryInteger.TOP_RATED, 1)
        moviePreviewViewModel.getMoviesList(this, MovieCategoryInteger.POPULAR, 1)
        moviePreviewViewModel.getMoviesList(this, MovieCategoryInteger.NOW_PLAYING, 1)
        moviePreviewViewModel.getMoviesList(this, MovieCategoryInteger.UPCOMING, 1)
    }

    private fun populateView(layout: LinearLayout, movies: List<MovieBasic>) {
        var layoutWidth = 0
        layout.doOnLayout {
            layoutWidth = it.measuredWidth
        }
        var i = 0
        var layoutWidthCovered = 105
        while(i < movies.size && layoutWidthCovered < layoutWidth) {
            val itemView = SquareItemView(this)
            itemView.setUiInfo(movies[i])
            layout.addView(itemView)
            i++
            layoutWidthCovered += 105
        }
    }

    override fun onClick(movieCategoryInteger: MovieCategoryInteger) {
            val intent = Intent(this, MovieListActivity::class.java).apply {
                putExtra(MAIN_ACTIVITY_MESSAGE_KEY, movieCategoryInteger.category)
            }
            startActivity(intent)
    }
}

interface OnClickShowAllItem{
    fun onClick(movieCategoryInteger: MovieCategoryInteger)
}