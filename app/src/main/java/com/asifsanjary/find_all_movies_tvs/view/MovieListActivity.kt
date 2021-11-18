package com.asifsanjary.find_all_movies_tvs.view

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.asifsanjary.find_all_movies_tvs.FindAllMoviesTvApplication
import com.asifsanjary.find_all_movies_tvs.databinding.ActivityMovieListBinding
import com.asifsanjary.find_all_movies_tvs.repository.local.entities.MovieBasic
import com.asifsanjary.find_all_movies_tvs.view.adapters.MovieListViewAdapter
import com.asifsanjary.find_all_movies_tvs.view.utils.MovieCategoryInteger
import com.asifsanjary.find_all_movies_tvs.view.utils.MovieCategoryString
import com.asifsanjary.find_all_movies_tvs.view.utils.ViewConstants.MAIN_ACTIVITY_MESSAGE_KEY
import com.asifsanjary.find_all_movies_tvs.view.utils.ViewConstants.PAGE_LIMIT
import com.asifsanjary.find_all_movies_tvs.view.utils.ViewConstants.PAGE_START

class MovieListActivity : AppCompatActivity() {

    private val className : String = "MovieListActivity"

    private val moviePreviewViewModel: MoviePreviewViewModel by viewModels {
        MoviePreviewViewModelFactory((application as FindAllMoviesTvApplication).repository)
    }

    private lateinit var binding: ActivityMovieListBinding
    private lateinit var movieListViewAdapter : MovieListViewAdapter
    private var currentMovieCategoryInteger: MovieCategoryInteger? = null
    private var currentMovieCategoryString: MovieCategoryString? = null
    private lateinit var layoutManager: LinearLayoutManager

    private var currentPageNo: Int = PAGE_START
    private var isLastPage = false
    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMovieListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        observeMovieGetter(intent.getIntExtra(MAIN_ACTIVITY_MESSAGE_KEY, -1))
    }

    private fun initView() {
        layoutManager = LinearLayoutManager(this)

        binding.recyclerView.addOnScrollListener(object : PaginationScrollListener(layoutManager) {
            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun loadMoreItems() {
                currentPageNo++
                fetchFromApi(currentMovieCategoryInteger!!, currentPageNo)
            }

        })

        movieListViewAdapter = MovieListViewAdapter(mutableListOf())
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = movieListViewAdapter

//        Decorator no needed for now
//        val itemDecorator = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
//        itemDecorator.setDrawable(getDrawable(R.drawable.adapter_divider)!!)
//        binding.recyclerView.addItemDecoration(itemDecorator)
    }

    private fun observeMovieGetter(movieCategory : Int) {

        when (movieCategory) {
            MovieCategoryInteger.TOP_RATED.category -> {
                currentMovieCategoryInteger = MovieCategoryInteger.TOP_RATED
                currentMovieCategoryString = MovieCategoryString.TOP_RATED
                moviePreviewViewModel.topRatedMoviesList.observe(this, Observer { list-> updateAdapterList(list) })
            }

            MovieCategoryInteger.POPULAR.category -> {
                currentMovieCategoryInteger = MovieCategoryInteger.POPULAR
                currentMovieCategoryString = MovieCategoryString.POPULAR
                moviePreviewViewModel.popularMoviesList.observe(this, Observer { list-> updateAdapterList(list) })
            }
            MovieCategoryInteger.NOW_PLAYING.category -> {
                currentMovieCategoryInteger = MovieCategoryInteger.NOW_PLAYING
                currentMovieCategoryString = MovieCategoryString.NOW_PLAYING
                moviePreviewViewModel.nowPlayingMoviesList.observe(this, Observer { list-> updateAdapterList(list) })
            }
            MovieCategoryInteger.UPCOMING.category -> {
                currentMovieCategoryInteger = MovieCategoryInteger.UPCOMING
                currentMovieCategoryString = MovieCategoryString.UPCOMING
                moviePreviewViewModel.upcomingMoviesList.observe(this, Observer { list-> updateAdapterList(list) })
            }
            else -> {
                Log.d(className, "Movie-Category  $movieCategory Not Found")
            }
        }

        currentMovieCategoryInteger?.let {
            setSupportActionBarTitle(currentMovieCategoryString!!.category)
            fetchFromApi(currentMovieCategoryInteger!!, currentPageNo)
        }
    }

    private fun setSupportActionBarTitle(actionBarName : String) {
        supportActionBar?.title = "$actionBarName Movies"
    }

    private fun fetchFromApi(movieCategoryInteger: MovieCategoryInteger, pageNo: Int) {
        when {
            currentPageNo in (PAGE_START + 1) until PAGE_LIMIT -> {
                isLoading = true
                movieListViewAdapter.updateLoading(isLoading)
            }
            currentPageNo > PAGE_LIMIT -> {
                return
            }
        }
        moviePreviewViewModel.getMoviesList(movieCategoryInteger, pageNo)
    }

    private fun updateAdapterList(movieList : List<MovieBasic>?) {
        movieList?.let{
            isLoading = false
            if(currentPageNo != PAGE_START) movieListViewAdapter.updateLoading(isLoading)

            movieListViewAdapter.updateList(movieList.toMutableList())

            if(currentPageNo == PAGE_LIMIT) {
                isLastPage = true
                movieListViewAdapter.updateLastPage(isLastPage)
            }
        }

        Log.d(
            className, "Movie-Category [$currentMovieCategoryInteger] = Quantity [${movieList?.size}]"
        )
    }
}

abstract class PaginationScrollListener
    (var layoutManager: LinearLayoutManager) : RecyclerView.OnScrollListener() {

    abstract fun isLastPage(): Boolean
    abstract fun isLoading(): Boolean

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

        if (!isLoading() && !isLastPage()) {
            if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
                loadMoreItems()
            }
        }
    }

    abstract fun loadMoreItems()
}