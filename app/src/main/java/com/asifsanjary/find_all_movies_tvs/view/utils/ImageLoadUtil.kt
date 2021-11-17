package com.asifsanjary.find_all_movies_tvs.view.utils

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.request.RequestOptions
import com.asifsanjary.find_all_movies_tvs.R

class ImageLoadUtil{

    companion object {

        private const val imageUrlPrefix = "https://image.tmdb.org/t/p/w185"

        fun loadImage(imageView: ImageView, url: String, drawable: CircularProgressDrawable) {
            val requestOptions = RequestOptions()
                .placeholder(drawable)
                .error(R.drawable.ic_baseline_movie_filter_24)

            GlideApp.with(imageView.context)
                .setDefaultRequestOptions(requestOptions)
                .load(imageUrlPrefix + url)
                .into(imageView)
        }

        fun getDrawable(context : Context) : CircularProgressDrawable {
            val drawable = CircularProgressDrawable(context)
            drawable.strokeWidth = 10f
            drawable.centerRadius = 50f
            drawable.start()

            return drawable
        }

        fun loadDefaultImage(imageView: ImageView) {
            GlideApp.with(imageView.context)
                .load(R.drawable.ic_baseline_movie_filter_24)
                .into(imageView)
        }
    }
}
