package com.example.myweatherapp.data.weatherRepository.modelMappers

import com.example.myweatherapp.data.weatherRepository.local.model.WeatherEntity
import com.example.myweatherapp.data.weatherRepository.local.model.WeatherForecastEntity
import com.example.myweatherapp.data.weatherRepository.local.model.WeatherWithWeatherForecast
import com.example.myweatherapp.data.weatherRepository.network.model.WeatherNetwork
import com.example.myweatherapp.model.Weather
import com.example.myweatherapp.model.WeatherForecast
import java.text.SimpleDateFormat
import java.time.Instant


fun WeatherWithWeatherForecast.asExternalModel() = Weather(
    weatherId = weatherEntity.weatherId,
    city = weatherEntity.city,
    country = weatherEntity.country,
    latitude = weatherEntity.latitude,
    longitude = weatherEntity.longitude,
    timestamp = weatherEntity.timestamp,
    weatherForecast = weatherForecastEntities.map(WeatherForecastEntity::asExternalModel)
)

fun WeatherForecastEntity.asExternalModel() = WeatherForecast(
    currentTemp = currentTemp,
    maxTemp = maxTemp,
    minTemp = minTemp,
    weatherDescription = weatherDescription, //Sky Is Clear
    date = date, //Fri, Aug 4
    atmosphericPressure = atmosphericPressure,
    humidity = humidity,
    windSpeed = windSpeed,
    sunrise = sunrise,
    sunset = sunset,
    iconId = iconId,
    cloudiness = cloudiness,
    probabilityOfPrecipitation = probabilityOfPrecipitation
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

fun formatDate(timestamp: Int): String {
    val sdf = SimpleDateFormat("EEE, MMM d")
    val date = java.util.Date(timestamp.toLong() * 1000)

    return sdf.format(date)
}

fun formatTime(timestamp: Int): String {
    val sdf = SimpleDateFormat("hh:mm:aa")
    val date = java.util.Date(timestamp.toLong() * 1000)

    return sdf.format(date)
}

fun formatDecimals(item: Double): String {
    return " %.0f".format(item)
}