package com.asifsanjary.find_all_movies_tvs.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.asifsanjary.find_all_movies_tvs.R
import com.asifsanjary.find_all_movies_tvs.repository.local.entities.MovieBasic
import com.asifsanjary.find_all_movies_tvs.view.utils.AdapterViewType
import com.asifsanjary.find_all_movies_tvs.view.utils.ImageLoadUtil
import com.asifsanjary.find_all_movies_tvs.view.utils.ViewConstants.DUMMY_MOVIE_DATA

class MovieListViewAdapter(private val movieItems: MutableList<MovieBasic>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var isLoading : Boolean = false
    private var isLastPage : Boolean = false

    fun updateList(newMovieItems: MutableList<MovieBasic>) {
        if(movieItems.hashCode() == newMovieItems.hashCode()) return

        val positionStart = movieItems.size
        val newItemCount = newMovieItems.size

        movieItems.addAll(newMovieItems)
        notifyItemRangeInserted(positionStart, newItemCount)
    }

    class NormalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var moviePosterView : ImageView = itemView.findViewById(R.id.movieBackdropView)
        private var moviePopularityScoreText : TextView = itemView.findViewById(R.id.moviePopularityPercentage)
        private var movieNameText : TextView = itemView.findViewById(R.id.movieNameText)
        private var movieGenresText : TextView = itemView.findViewById(R.id.movieGenresText)
        private var movieOverviewText : TextView = itemView.findViewById(R.id.movieOverviewText)

        fun bind(movieItem: MovieBasic) {
            moviePopularityScoreText.text = (movieItem.voteAverage * 10).toInt().toString().plus("%")
            movieNameText.text = movieItem.title
            movieOverviewText.text = movieItem.overview
            movieGenresText.text = movieItem.genres

            if(!movieItem.backdropPath.isNullOrEmpty()) {
                ImageLoadUtil.loadImage(
                    moviePosterView,
                    movieItem.backdropPath,
                    ImageLoadUtil.getDrawable(itemView.context)
                )
            }
            else{
                ImageLoadUtil.loadDefaultImage(
                    moviePosterView
                )
            }
        }
    }

    class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    class LastItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            AdapterViewType.LOADING.viewType -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_loading_view, parent, false)
                LoadingViewHolder(view)
            }
            AdapterViewType.LAST_PAGE.viewType -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_last_item_view, parent, false)
                LastItemViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_list_item_view, parent, false)
                NormalViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            AdapterViewType.NORMAL.viewType -> (holder as NormalViewHolder).bind(movieItems[position])
        }
    }

    override fun getItemCount(): Int {
        return movieItems.size
    }

    override fun getItemViewType(position: Int): Int {
        if (position == movieItems.size - 1) {
            return when {
                isLastPage -> {
                    AdapterViewType.LAST_PAGE.viewType
                }
                isLoading -> {
                    AdapterViewType.LOADING.viewType
                }
                else -> {
                    AdapterViewType.NORMAL.viewType
                }
            }
        }
        else {
            return AdapterViewType.NORMAL.viewType
        }
    }

    fun updateLoading(loading: Boolean) {
        isLoading = loading
        if(isLoading) {
            movieItems.add(DUMMY_MOVIE_DATA)
            notifyItemInserted(movieItems.size - 1)
        }
        else{
            val position: Int = movieItems.size - 1
            val item: MovieBasic = movieItems[position]
            if (item == DUMMY_MOVIE_DATA) {
                movieItems.removeAt(position)
                notifyItemRemoved(position)
            }
        }
    }

    fun updateLastPage(lastPage: Boolean) {
        isLastPage = lastPage
        if(isLastPage) {
            movieItems.add(DUMMY_MOVIE_DATA)
            notifyItemInserted(movieItems.size - 1)
        }
    }
}