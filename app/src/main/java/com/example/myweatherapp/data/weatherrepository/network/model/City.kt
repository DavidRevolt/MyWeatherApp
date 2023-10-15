package com.example.myweatherapp.data.weatherrepository.network.model

data class City(
    val coord: Coord,
    val country: String,
    val id: Int,
    val name: String,
    val population: Int,
    val timezone: Int
)