package com.example.myweatherapp.data.weatherRepository.local.model

import androidx.room.Embedded
import androidx.room.Relation
import com.example.myweatherapp.model.Weather


data class WeatherWithWeatherForecast(
    @Embedded
    val weatherEntity: WeatherEntity,
    @Relation(
        parentColumn = "weatherId",
        entityColumn = "weatherCreatorId"
    )
    val weatherForecastEntities: List<WeatherForecastEntity>
)


fun WeatherWithWeatherForecast.asExternalModel() = Weather(
    weatherId = weatherEntity.weatherId,
    city = weatherEntity.city,
    country = weatherEntity.country,
    latitude = weatherEntity.latitude,
    longitude = weatherEntity.longitude,
    timestamp = weatherEntity.timestamp,
    weatherForecast = weatherForecastEntities.map(WeatherForecastEntity::asExternalModel)
)