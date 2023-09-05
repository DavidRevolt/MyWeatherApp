package com.example.myweatherapp.di.servicesModules

import com.example.myweatherapp.Services.location.LocationService
import com.example.myweatherapp.Services.location.LocationServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class LocationServiceModule {
    @Binds
    abstract fun bindsLocationService(locationServiceImpl: LocationServiceImpl): LocationService
}