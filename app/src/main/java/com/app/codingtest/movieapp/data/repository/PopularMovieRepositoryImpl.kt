package com.app.codingtest.movieapp.data.repository

import com.app.codingtest.movieapp.BuildConfig
import com.app.codingtest.movieapp.data.model.popularmovie.Result
import com.app.codingtest.movieapp.data.network.MovieService
import com.app.codingtest.movieapp.domain.entity.asMovie
import com.app.codingtest.movieapp.domain.entity.popularmovie.PopularMovieInfo
import com.app.codingtest.movieapp.domain.repository.PopularMovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class PopularMovieRepositoryImpl (private val movieService: MovieService) :
    PopularMovieRepository {
    override suspend fun getMovies(page: Int): Flow<PopularMovieInfo> {
        return flow {
            emit(movieService.getPopularMovies(BuildConfig.API_KEY, page))
        }.map { popularMovieData ->
            val data = popularMovieData.results.map(Result::asMovie).toMutableList()
            PopularMovieInfo(data, popularMovieData.totalPages)
        }.flowOn(Dispatchers.IO)
    }
}