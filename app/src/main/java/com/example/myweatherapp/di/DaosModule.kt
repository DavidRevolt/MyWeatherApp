package com.example.myweatherapp.di

import com.example.myweatherapp.data.AppDatabase
import com.example.myweatherapp.data.recentsearchrepository.local.RecentSearchQueryDao
import com.example.myweatherapp.data.weatherrepository.local.WeatherDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaosModule {
    @Singleton
    @Provides
    fun provideWeatherDao(database: AppDatabase): WeatherDao =
        database.weatherDao()

    @Singleton
    @Provides
    fun provideRecentSearchQueryDao(database: AppDatabase): RecentSearchQueryDao =
        database.recentSearchQueryDao()

}