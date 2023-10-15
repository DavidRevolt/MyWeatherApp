package com.example.myweatherapp.di.servicesmodules

import com.example.myweatherapp.services.location.LocationService
import com.example.myweatherapp.services.location.LocationServiceImpl
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