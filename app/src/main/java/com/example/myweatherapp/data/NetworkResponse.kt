package com.example.myweatherapp.data

sealed interface NetworkResponse<out T> {
    data class Success<T>(val data: T) : NetworkResponse<T>
    data class Error(val exception: Throwable? = null) : NetworkResponse<Nothing>
}