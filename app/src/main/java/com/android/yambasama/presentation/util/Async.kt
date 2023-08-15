package com.android.yambasama.presentation.util

sealed class Async<out T> {
    object Loading : Async<Nothing>()

    data class Error(val errorMessage: Int): Async<Nothing>()

    data class Success<out T>(val data: T) : Async<T>()
}