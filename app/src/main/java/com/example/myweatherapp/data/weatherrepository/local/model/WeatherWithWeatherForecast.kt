package com.example.myweatherapp.data.weatherrepository.local.model

import androidx.room.Embedded
import androidx.room.Relation


data class WeatherWithWeatherForecast(
    @Embedded
    val weatherEntity: WeatherEntity,
    @Relation(
        parentColumn = "weatherId",
        entityColumn = "weatherCreatorId"
    )
    val weatherForecastEntities: List<WeatherForecastEntity>
)
