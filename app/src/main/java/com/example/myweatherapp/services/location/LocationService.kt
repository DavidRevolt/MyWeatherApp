package com.example.myweatherapp.services.location

import android.location.Location

interface LocationService {
    suspend fun getLocation(): Result<Location?>
}