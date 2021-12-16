package com.app.codingtest.movieapp.domain.repository

import com.app.codingtest.movieapp.domain.entity.popularmovie.PopularMovieInfo
import kotlinx.coroutines.flow.Flow

interface PopularMovieRepository {
    suspend fun getMovies(page: Int): Flow<PopularMovieInfo>
}