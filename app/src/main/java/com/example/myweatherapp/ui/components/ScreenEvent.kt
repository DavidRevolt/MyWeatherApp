package com.example.myweatherapp.ui.components

sealed interface ScreenEvent<out T>  {
    data class Success<T>(val data: T)  : ScreenEvent<T>
    data class Error(val exception: Throwable? = null) : ScreenEvent<Nothing>
    object Loading : ScreenEvent<Nothing>
}