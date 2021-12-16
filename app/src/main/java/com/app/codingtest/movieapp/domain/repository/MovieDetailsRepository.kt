package com.app.codingtest.movieapp.domain.repository

import com.app.codingtest.movieapp.domain.entity.moviedetails.MovieDetails
import kotlinx.coroutines.flow.Flow

interface MovieDetailsRepository {
    suspend fun getMoviesDetails(movieId: Int): Flow<MovieDetails>

}