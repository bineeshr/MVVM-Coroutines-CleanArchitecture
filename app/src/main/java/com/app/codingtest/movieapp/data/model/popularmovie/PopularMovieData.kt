package com.app.codingtest.movieapp.data.model.popularmovie


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PopularMovieData(
    @Json(name = "page")
    val page: Int,
    @Json(name = "results")
    val results: List<Result> = emptyList(),
    @Json(name = "total_pages")
    val totalPages: Int,
    @Json(name = "total_results")
    val totalResults: Int
)