package com.example.myweatherapp.data.userdatarepository

import kotlinx.coroutines.flow.Flow

interface UserDataRepository {
    suspend fun setWeatherIndexToFocusOn(index: Int)
    fun getWeatherIndexToFocusOn(): Flow<Int>
}