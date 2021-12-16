package com.app.codingtest.movieapp.ui.popularmovie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.codingtest.movieapp.databinding.ItemMovieBinding
import com.app.codingtest.movieapp.databinding.LoadMoreProgressBinding
import com.app.codingtest.movieapp.domain.entity.popularmovie.PopularMovie

class MovieAdapter(
    private var movies: MutableList<PopularMovie> = mutableListOf(),
    val onMovieClick: (PopularMovie) -> Unit
) : RecyclerView.Adapter<BaseViewHolder>() {
    private var isLoadingAdded: Boolean = false

    companion object {
        private const val ITEM = 0
        private const val LOADING = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            ITEM -> {
                val binding = ItemMovieBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)
                MovieViewHolder(binding,onMovieClick)
            }
            LOADING -> {
                val binding = LoadMoreProgressBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)
                LoadingViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Wrong view type")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == movies.size - 1 && isLoadingAdded) LOADING else ITEM
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        when (getItemViewType(position)) {
            ITEM -> {
                val movie = movies[position]
                (holder as MovieViewHolder).bind(movie)
            }
            LOADING -> {}
        }
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    fun updateList(movies: MutableList<PopularMovie>) {
        this.movies.addAll(movies)
        notifyItemChanged(movies.size)
    }

    fun addLoadingFooter() {
        isLoadingAdded = true
    }

    fun removeLoadingFooter() {
        isLoadingAdded = false
    }
}