package com.asifsanjary.find_all_movies_tvs.view

import android.util.Log
import androidx.lifecycle.*
import com.asifsanjary.find_all_movies_tvs.repository.Repository
import com.asifsanjary.find_all_movies_tvs.repository.local.entities.MovieBasic
import com.asifsanjary.find_all_movies_tvs.view.utils.MovieCategoryInteger
import kotlinx.coroutines.launch

class MoviePreviewViewModel(private val repository: Repository) : ViewModel() {

    private val className : String = "MoviePreviewViewModel"

    private val topRatedMoviesListMt : MutableLiveData<List<MovieBasic>> = MutableLiveData()
    val topRatedMoviesList : LiveData<List<MovieBasic>> = topRatedMoviesListMt

    private val popularMoviesListMt : MutableLiveData<List<MovieBasic>> = MutableLiveData()
    val popularMoviesList : LiveData<List<MovieBasic>> = popularMoviesListMt

    private val nowPlayingMoviesListMt : MutableLiveData<List<MovieBasic>> = MutableLiveData()
    val nowPlayingMoviesList : LiveData<List<MovieBasic>> = nowPlayingMoviesListMt

    private val upcomingMoviesListMt : MutableLiveData<List<MovieBasic>> = MutableLiveData()
    val upcomingMoviesList : LiveData<List<MovieBasic>> = upcomingMoviesListMt

    fun getMoviesList(movieCategoryInteger: MovieCategoryInteger, pageNo: Int) = viewModelScope.launch{
        repository.getMoviePreviewDataList(movieCategoryInteger, pageNo, object :
            OnResponseVm {
            override fun onReceived(movieList: List<MovieBasic>) {
                Log.d(className, "${movieList.size.toString()} $movieCategoryInteger Movies Received")
                when (movieCategoryInteger) {
                    MovieCategoryInteger.UPCOMING -> upcomingMoviesListMt.postValue(movieList)
                    MovieCategoryInteger.POPULAR -> popularMoviesListMt.postValue(movieList)
                    MovieCategoryInteger.NOW_PLAYING -> nowPlayingMoviesListMt.postValue(movieList)
                    MovieCategoryInteger.TOP_RATED -> topRatedMoviesListMt.postValue(movieList)
                }
            }

            override fun onError(errorMsg: String) {
                //TODO: Handle Page End - No Internet Cases
                Log.d(className, "Error: $movieCategoryInteger Movies:\n$errorMsg")
            }

        })
    }
}

class MoviePreviewViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MoviePreviewViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MoviePreviewViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

interface OnResponseVm{
    fun onReceived(movieList: List<MovieBasic>)
    fun onError(errorMsg : String)
}