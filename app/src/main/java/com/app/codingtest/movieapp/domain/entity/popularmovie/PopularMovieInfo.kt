package com.app.codingtest.movieapp.domain.entity.popularmovie

import com.app.codingtest.movieapp.domain.entity.popularmovie.PopularMovie

data class PopularMovieInfo(val popularMovies: MutableList<PopularMovie>, val totalPage: Int)