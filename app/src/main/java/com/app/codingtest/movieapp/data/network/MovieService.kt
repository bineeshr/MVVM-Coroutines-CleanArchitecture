package com.app.codingtest.movieapp.data.network

import com.app.codingtest.movieapp.data.model.moviedetails.MovieDetailsData
import com.app.codingtest.movieapp.data.model.popularmovie.PopularMovieData
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): PopularMovieData

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String,
    ): MovieDetailsData
}