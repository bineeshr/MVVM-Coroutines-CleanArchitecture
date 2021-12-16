package com.app.codingtest.movieapp.ui.popularmovie.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.codingtest.movieapp.common.MovieResult
import com.app.codingtest.movieapp.domain.entity.moviedetails.MovieDetails
import com.app.codingtest.movieapp.domain.usecases.GetMovieDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularMovieDetailsViewModel @Inject constructor(private val getMovieDetails: GetMovieDetails) :
    ViewModel() {
    private val _movieDetailState = MutableStateFlow<MovieResult<MovieDetails>>(MovieResult.Empty)
    val movieDetailsState get() = _movieDetailState
    fun fetchMovieDetails(movieId: Int) {
        viewModelScope.launch {
            getMovieDetails.invoke(movieId).onStart {
                _movieDetailState.value = MovieResult.Loading(true)
            }.catch { e ->
                _movieDetailState.value = MovieResult.Loading(false)
                _movieDetailState.value = MovieResult.Error(Exception(e))
            }.collect { movieDetails ->
                _movieDetailState.value = MovieResult.Loading(false)
                _movieDetailState.value = MovieResult.Success(movieDetails)
            }
        }
    }
}