package com.example.myweatherapp.di.servicesModules

import com.example.myweatherapp.services.NetworkMonitor.NetworkMonitorService
import com.example.myweatherapp.services.NetworkMonitor.NetworkMonitorServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkMonitorServiceModule {
    @Binds
    abstract fun bindsNetworkMonitorService(NetworkMonitorServiceImpl: NetworkMonitorServiceImpl): NetworkMonitorService
}