package com.app.codingtest.movieapp.domain.usecases

import com.app.codingtest.movieapp.domain.repository.MovieDetailsRepository
import javax.inject.Inject

class GetMovieDetails @Inject constructor(
    private val movieDetailsRepository: MovieDetailsRepository
) {
    suspend operator fun invoke(movieId: Int) = movieDetailsRepository.getMoviesDetails(movieId)
}