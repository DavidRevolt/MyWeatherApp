package com.example.myweatherapp.di

import com.example.myweatherapp.data.recentsearchrepository.RecentSearchRepository
import com.example.myweatherapp.data.recentsearchrepository.RecentSearchRepositoryImpl
import com.example.myweatherapp.data.userdatarepository.UserDataRepository
import com.example.myweatherapp.data.userdatarepository.UserDataRepositoryImpl
import com.example.myweatherapp.data.weatherrepository.WeatherRepository
import com.example.myweatherapp.data.weatherrepository.WeatherRepositoryImpl
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