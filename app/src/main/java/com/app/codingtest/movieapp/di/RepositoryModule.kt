package com.app.codingtest.movieapp.di

import com.app.codingtest.movieapp.data.network.MovieService
import com.app.codingtest.movieapp.data.repository.MovieDetailsRepositoryImpl
import com.app.codingtest.movieapp.data.repository.PopularMovieRepositoryImpl
import com.app.codingtest.movieapp.domain.repository.MovieDetailsRepository
import com.app.codingtest.movieapp.domain.repository.PopularMovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideMovieRepository(movieService: MovieService): PopularMovieRepository {
        return PopularMovieRepositoryImpl(movieService)
    }

    @Provides
    @Singleton
    fun provideMovieDetailsRepository(movieService: MovieService): MovieDetailsRepository {
        return MovieDetailsRepositoryImpl(movieService)
    }
}