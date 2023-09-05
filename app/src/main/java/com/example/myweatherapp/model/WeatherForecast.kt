package com.example.myweatherapp.model

data class WeatherForecast(
    val currentTemp: String,
    val maxTemp: String,
    val minTemp: String,
    val weatherDescription: String, //Sky Is Clear
    val date: String, //Fri, Aug 4
    val atmosphericPressure: String,
    val humidity: String,
    val windSpeed: String,
    val sunrise: String,
    val sunset: String,
    val iconId: String,
    //Percentage
    val cloudiness: String,
    val probabilityOfPrecipitation: String
)
