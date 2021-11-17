package com.asifsanjary.find_all_movies_tvs.view.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.asifsanjary.find_all_movies_tvs.view.OnClickShowAllItem
import com.asifsanjary.find_all_movies_tvs.R
import com.asifsanjary.find_all_movies_tvs.view.utils.MovieCategoryInteger


class CategoryHeaderView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private var rootView : ConstraintLayout? = null
    private var categoryTitle : TextView? = null
    private var categoryShowAllButton : Button? = null

    init {
        rootView = LayoutInflater.from(context).inflate(R.layout.category_header_layout, this, true) as ConstraintLayout
        if(rootView != null) {
            categoryTitle = rootView!!.findViewById(R.id.categoryTitle)
            categoryShowAllButton = rootView!!.findViewById(R.id.categoryShowAllAction)
        }
    }

    fun setUp(title: String, movieCategoryInteger: MovieCategoryInteger, listener: OnClickShowAllItem) {
        categoryTitle?.text = title

        categoryShowAllButton?.setOnClickListener {
            listener.onClick(movieCategoryInteger)
        }
    }
}