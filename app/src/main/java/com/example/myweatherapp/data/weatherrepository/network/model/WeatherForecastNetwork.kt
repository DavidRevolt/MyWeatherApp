package com.example.myweatherapp.data.weatherrepository.network.model


data class WeatherForecastNetwork(
    val clouds: Int,
    val deg: Int,
    val dt: Int,
    val feels_like: FeelsLike?,
    val gust: Double,
    val humidity: Int,
    val pop: Double,
    val pressure: Int,
    val speed: Double,
    val sunrise: Int,
    val sunset: Int,
    val temp: Temp,
    val weather: List<WeatherForecastDescriptionNetwork>
)

