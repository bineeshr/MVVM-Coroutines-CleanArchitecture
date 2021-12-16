package com.app.codingtest.movieapp.ui.popularmovie

import com.app.codingtest.movieapp.databinding.ItemMovieBinding
import com.app.codingtest.movieapp.domain.entity.popularmovie.PopularMovie

class MovieViewHolder(
    private val binding: ItemMovieBinding,
    private val onMovieClick: (PopularMovie) -> Unit
) : BaseViewHolder(binding.root) {

    fun bind(movie: PopularMovie) {
        with(binding) {
            com.bumptech.glide.Glide.with(context).load(movie.imageUrl)
                .centerCrop()
                .into(imageMovie)
            textTitle.text = movie.title
            root.setOnClickListener {
                onMovieClick.invoke(movie)
            }
        }
    }
}