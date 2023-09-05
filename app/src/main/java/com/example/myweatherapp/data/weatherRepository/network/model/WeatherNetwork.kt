package com.example.myweatherapp.data.weatherRepository.network.model

import com.example.myweatherapp.data.weatherRepository.local.model.WeatherEntity
import com.example.myweatherapp.data.weatherRepository.local.model.WeatherForecastEntity
import com.example.myweatherapp.data.weatherRepository.local.model.WeatherWithWeatherForecast
import com.example.myweatherapp.utils.formatDate
import com.example.myweatherapp.utils.formatDecimals
import com.example.myweatherapp.utils.formatTime
import java.time.Instant


data class WeatherNetwork(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<WeatherForecastNetwork> = emptyList(),
    val message: Double
)

fun WeatherNetwork.asEntity() = WeatherWithWeatherForecast(
    weatherEntity = WeatherEntity(
        weatherId = city.id,
        city = city.name,
        country = city.country,
        latitude = city.coord.lat.toString(),
        longitude = city.coord.lon.toString(),
        timestamp = Instant.now()
    ),
    weatherForecastEntities = list.map { it ->
        (WeatherForecastEntity(
            weatherCreatorId = city.id,
            currentTemp = formatDecimals(it.temp.day),
            maxTemp = formatDecimals(it.temp.max),
            minTemp = formatDecimals(it.temp.min),
            weatherDescription = it.weather[0].description, //Sky Is Clear
            date = formatDate(it.dt), //Fri, Aug 4
            atmosphericPressure = it.pressure.toString(),
            humidity = it.humidity.toString(),
            windSpeed = it.speed.toString(),
            sunrise = formatTime(it.sunrise),
            sunset = formatTime(it.sunset),
            iconId = it.weather[0].icon,
            cloudiness = it.clouds.toString(),
            probabilityOfPrecipitation = (it.pop * 100).toString()
        ))
    }
)