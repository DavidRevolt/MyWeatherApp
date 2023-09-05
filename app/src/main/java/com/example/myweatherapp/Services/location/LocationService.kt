package com.example.myweatherapp.Services.location

import android.location.Location

interface LocationService {
    suspend fun getLocation(): Result<Location?>
}