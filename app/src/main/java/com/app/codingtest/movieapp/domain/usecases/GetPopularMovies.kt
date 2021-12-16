package com.app.codingtest.movieapp.domain.usecases

import com.app.codingtest.movieapp.domain.repository.PopularMovieRepository
import javax.inject.Inject

class GetPopularMovies @Inject constructor(
    private val popularMovieRepository: PopularMovieRepository
) {
    suspend operator fun invoke(page: Int) = popularMovieRepository.getMovies(page)
}