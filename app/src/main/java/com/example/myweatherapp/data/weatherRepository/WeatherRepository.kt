package com.example.myweatherapp.data.weatherRepository

import android.location.Location
import com.example.myweatherapp.data.utils.NetworkResponse
import com.example.myweatherapp.data.utils.Syncable
import com.example.myweatherapp.model.Weather
import kotlinx.coroutines.flow.Flow

interface WeatherRepository : Syncable {

    suspend fun getNewWeather(city: String): NetworkResponse<Weather>
    suspend fun getNewWeather(location: Location): NetworkResponse<Weather>
    fun getAllWeather(): Flow<List<Weather>>
    suspend fun deleteWeather(id: Int)
}