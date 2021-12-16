package com.app.codingtest.movieapp.data.repository

import com.app.codingtest.movieapp.BuildConfig
import com.app.codingtest.movieapp.data.network.MovieService
import com.app.codingtest.movieapp.domain.entity.asMovieDetail
import com.app.codingtest.movieapp.domain.entity.moviedetails.MovieDetails
import com.app.codingtest.movieapp.domain.repository.MovieDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class MovieDetailsRepositoryImpl(private val movieService: MovieService) : MovieDetailsRepository {

    override suspend fun getMoviesDetails(movieId: Int): Flow<MovieDetails> {
        return flow {
            emit(movieService.getMovieDetails(movieId, BuildConfig.API_KEY))
        }.map { movieDetailsData ->
            movieDetailsData.asMovieDetail()
        }
    }
}