package com.example.myweatherapp.Services.NetworkMonitor

import kotlinx.coroutines.flow.Flow

interface NetworkMonitorService {
    fun isOnline(): Flow<Boolean>
}