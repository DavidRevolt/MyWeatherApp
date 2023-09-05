package com.example.myweatherapp.data.weatherRepository.network.model

data class WeatherForecastDescriptionNetwork(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)