package com.example.myweatherapp.services.NetworkMonitor

import kotlinx.coroutines.flow.Flow

interface NetworkMonitorService {
    fun isOnline(): Flow<Boolean>
}