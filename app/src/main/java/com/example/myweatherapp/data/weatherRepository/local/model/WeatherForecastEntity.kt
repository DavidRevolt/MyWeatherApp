package com.example.myweatherapp.data.weatherRepository.local.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "weatherForecast",
    //Constrains Between one to many, action on parent effect the children!!!
    foreignKeys = [ForeignKey(
        entity = WeatherEntity::class,
        parentColumns = arrayOf("weatherId"),
        childColumns = arrayOf("weatherCreatorId"),
        onDelete = ForeignKey.CASCADE // children will be automatically deleted if parent deleted
    )], indices = [Index("weatherCreatorId")]

)
data class WeatherForecastEntity(
    @PrimaryKey(autoGenerate = true) // network model doesn't come with id's
    val weatherForecastId: Int = 0,
    val weatherCreatorId: Int, //Parent of One To Many
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
    val cloudiness: String,
    val probabilityOfPrecipitation: String
)