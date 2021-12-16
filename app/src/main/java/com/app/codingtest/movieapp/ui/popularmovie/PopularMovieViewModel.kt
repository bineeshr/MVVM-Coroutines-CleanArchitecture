package com.app.codingtest.movieapp.ui.popularmovie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.codingtest.movieapp.common.MovieResult
import com.app.codingtest.movieapp.domain.entity.popularmovie.PopularMovie
import com.app.codingtest.movieapp.domain.usecases.GetPopularMovies
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class PopularMovieViewModel @Inject constructor(private val getPopularMovies: GetPopularMovies) :
    ViewModel() {
    private val _movieState =
        MutableStateFlow<MovieResult<MutableList<PopularMovie>>>(MovieResult.Empty)
    val movieState get() = _movieState
    var movieTotalPage = 0
    fun fetchPopularMovies(page: Int) {
        viewModelScope.launch {
            getPopularMovies.invoke(page).onStart {
                if (page == 1) {
                    _movieState.value = MovieResult.Loading(true)
                }
            }.catch { e ->
                _movieState.value = MovieResult.Loading(false)
                _movieState.value = MovieResult.Error(Exception(e))
            }.collect { movieInfo ->
                movieTotalPage = movieInfo.totalPage
                _movieState.value = MovieResult.Loading(false)
                _movieState.value = MovieResult.Success(movieInfo.popularMovies)
            }
        }
    }
}