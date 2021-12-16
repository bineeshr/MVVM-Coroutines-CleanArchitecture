package com.app.codingtest.movieapp.common


sealed class MovieResult<out T> {
    data class Success<T>(val data: T) : MovieResult<T>()
    data class Error(val exception: Exception) : MovieResult<Nothing>()
    data class Loading(val showProgress: Boolean) : MovieResult<Nothing>()
    object Empty : MovieResult<Nothing>()
}