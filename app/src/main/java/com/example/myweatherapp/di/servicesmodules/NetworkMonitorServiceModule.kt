package com.example.myweatherapp.di.servicesmodules

import com.example.myweatherapp.services.networkmonitor.NetworkMonitorService
import com.example.myweatherapp.services.networkmonitor.NetworkMonitorServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkMonitorServiceModule {
    @Binds
    abstract fun bindsNetworkMonitorService(networkMonitorServiceImpl: NetworkMonitorServiceImpl): NetworkMonitorService
}