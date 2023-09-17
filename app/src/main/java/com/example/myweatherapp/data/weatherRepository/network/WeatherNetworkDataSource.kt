package com.example.myweatherapp.data.weatherRepository.network

import com.example.myweatherapp.data.weatherRepository.network.model.WeatherNetwork
import com.example.myweatherapp.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherNetworkDataSource {

    @GET(value = "data/2.5/forecast/daily")
    suspend fun getWeatherByCity(
        @Query("q") city: String,
        @Query("units") units: String = "metric",
        @Query("appid") appid: String = Constants.API_KEY
    ): WeatherNetwork


    @GET(value = "data/2.5/forecast/daily")
    suspend fun getWeatherByCoordinates(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("units") units: String = "metric",
        @Query("appid") appid: String = Constants.API_KEY
    ): WeatherNetwork


    //Get By City ID, Used for sync room with network
    @GET(value = "data/2.5/forecast/daily")
    suspend fun getWeatherByCityId(
        @Query("id") cityId: Int,
        @Query("units") units: String = "metric",
        @Query("appid") appid: String = Constants.API_KEY
    ): WeatherNetwork

}




