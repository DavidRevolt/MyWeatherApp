package com.example.myweatherapp.di

import com.example.myweatherapp.data.weatherrepository.network.WeatherNetworkDataSource
import com.example.myweatherapp.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideWeatherNetworkDataSource(): WeatherNetworkDataSource {
        return Retrofit.Builder()
            .baseUrl(Constants.BACKEND_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherNetworkDataSource::class.java) //The Interface
    }
}