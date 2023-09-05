package com.example.myweatherapp.model

import java.time.Instant

data class Weather(
    val weatherId: Int,
    val city: String,
    val country: String,
    val latitude: String,
    val longitude: String,
    val weatherForecast: List<WeatherForecast> = emptyList(),
    val timestamp: Instant
)
