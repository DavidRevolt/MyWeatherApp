package com.example.myweatherapp.services.networkmonitor

import kotlinx.coroutines.flow.Flow

interface NetworkMonitorService {
    fun isOnline(): Flow<Boolean>
}