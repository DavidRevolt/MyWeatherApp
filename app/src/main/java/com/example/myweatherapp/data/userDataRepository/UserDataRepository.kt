package com.example.myweatherapp.data.userDataRepository

import kotlinx.coroutines.flow.Flow

interface UserDataRepository {
    suspend fun setWeatherIndexToFocusOn(index: Int)
    fun getWeatherIndexToFocusOn(): Flow<Int>
}