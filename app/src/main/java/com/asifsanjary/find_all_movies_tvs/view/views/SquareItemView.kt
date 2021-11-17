package com.asifsanjary.find_all_movies_tvs.view.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.asifsanjary.find_all_movies_tvs.R
import com.asifsanjary.find_all_movies_tvs.repository.local.entities.MovieBasic
import com.asifsanjary.find_all_movies_tvs.view.utils.ImageLoadUtil


class SquareItemView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    private var rootView : LinearLayout? = null
    private var itemPosterView : ImageView? = null
    private var itemTitle : TextView? = null
    private var itemSubtitle : TextView? = null

    init {
        rootView = LayoutInflater.from(context).inflate(R.layout.movie_item_square_view, this, true) as LinearLayout
        if(rootView != null) {
            itemPosterView = rootView!!.findViewById(R.id.square_item_image_view)
            itemTitle = rootView!!.findViewById(R.id.square_item_title)
            itemSubtitle = rootView!!.findViewById(R.id.square_item_subtitle)
        }
    }

    fun setUiInfo(item: MovieBasic) {
        itemTitle?.text = item.title
        itemSubtitle?.text = item.releaseDate
        item.posterPath?.let {
            ImageLoadUtil.loadImage(itemPosterView!!,
                it, ImageLoadUtil.getDrawable(context))
        }
    }
}