package com.example.myweatherapp.data.weatherRepository.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant


@Entity(tableName = "weather")
data class WeatherEntity(
    @PrimaryKey
    val weatherId :Int,
    val city: String,
    val country: String,
    val latitude: String,
    val longitude: String,
    val timestamp: Instant
)

