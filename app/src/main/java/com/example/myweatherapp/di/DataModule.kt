package com.example.myweatherapp.di

import com.example.myweatherapp.data.recentSearchRepository.RecentSearchRepository
import com.example.myweatherapp.data.recentSearchRepository.RecentSearchRepositoryImpl
import com.example.myweatherapp.data.userDataRepository.UserDataRepository
import com.example.myweatherapp.data.userDataRepository.UserDataRepositoryImpl
import com.example.myweatherapp.data.weatherRepository.WeatherRepository
import com.example.myweatherapp.data.weatherRepository.WeatherRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindsWeatherRepository(weatherRepositoryImpl: WeatherRepositoryImpl): WeatherRepository

    @Binds
    abstract fun bindsUserDataRepository(userDataRepositoryImpl: UserDataRepositoryImpl): UserDataRepository

    @Binds
    abstract fun bindsURecentSearchRepository(recentSearchRepositoryImpl: RecentSearchRepositoryImpl): RecentSearchRepository
}