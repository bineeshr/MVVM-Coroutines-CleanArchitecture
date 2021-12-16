package com.app.codingtest.movieapp.domain.entity

import com.app.codingtest.movieapp.common.IMAGE_URL_PATH
import com.app.codingtest.movieapp.data.model.moviedetails.MovieDetailsData
import com.app.codingtest.movieapp.data.model.popularmovie.Result
import com.app.codingtest.movieapp.domain.entity.moviedetails.MovieDetails
import com.app.codingtest.movieapp.domain.entity.popularmovie.PopularMovie


internal fun Result.asMovie(): PopularMovie = PopularMovie(
    id = id,
    title = title,
    imageUrl = "$IMAGE_URL_PATH$backdropPath"
)

internal fun MovieDetailsData.asMovieDetail(): MovieDetails = MovieDetails(
    title = title,
    imageUrl = "$IMAGE_URL_PATH$backdropPath",
    rating = voteAverage,
    overView = overview
)