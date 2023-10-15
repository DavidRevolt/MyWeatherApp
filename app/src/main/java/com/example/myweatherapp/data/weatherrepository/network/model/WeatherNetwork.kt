package com.example.myweatherapp.data.weatherrepository.network.model


data class WeatherNetwork(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<WeatherForecastNetwork> = emptyList(),
    val message: Double
)
